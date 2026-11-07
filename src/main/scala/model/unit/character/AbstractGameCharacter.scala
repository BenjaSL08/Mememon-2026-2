package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.action.Action
import cl.uchile.dcc.model.action.Attack
import cl.uchile.dcc.model.action.ConsumePotion
import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.action.Move
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.item.potion.FortitudePotion
import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.potion.MagicStrengthPotion
import cl.uchile.dcc.model.item.potion.ManaPotion
import cl.uchile.dcc.model.item.potion.Potion
import cl.uchile.dcc.model.item.weapon.Bow
import cl.uchile.dcc.model.item.weapon.Dagger
import cl.uchile.dcc.model.item.weapon.NoWeapon
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.item.weapon.Wand
import cl.uchile.dcc.model.item.weapon.Weapon
import cl.uchile.dcc.model.unit.AbstractGameUnit

import scala.collection.mutable

/**
 * Base implementation shared by every [[GameCharacter]].
 *
 * A character starts with [[NoWeapon]] in its slot and an empty inventory. By
 * default it cannot equip any weapon nor consume magic potions: each character
 * class overrides only the weapons it can equip, and magic characters override
 * the magic potions.
 *
 * @param name the name of the character.
 * @param maxHealthPoints the maximum health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 */
abstract class AbstractGameCharacter(name: String, maxHealthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameUnit(name, maxHealthPoints, defense, weight), GameCharacter:

  /** The weapon slot. Private so it only changes through [[equip]]. */
  private var _weapon: Weapon = NoWeapon

  /** The weapons in the inventory. Private so they only change through [[addWeapon]]. */
  private val _weapons = mutable.ListBuffer[Weapon]()

  /** The potions in the inventory. Private so they only change through [[addPotion]]. */
  private var _potions = mutable.ListBuffer[Potion]()

  override def weapon: Weapon = _weapon

  /**
   * The action bar of a character is its weight plus half the weight of its
   * weapon. [[NoWeapon]] weighs zero, so no special case is needed.
   */
  override def maxActionBar: Double = weight + 0.5 * weapon.weight

  /** A character attacks with its weapon, so it cannot attack without one. */
  override def attackDamage: Int = weapon.attackDamage

  /** Returns a new list, so changing it from outside is not possible. */
  override def inventory: List[Item] =
    val items = mutable.ListBuffer[Item]()
    for weapon <- _weapons do items += weapon
    for potion <- _potions do items += potion
    items.toList

  override def weapons: List[Weapon] = _weapons.toList

  override def potions: List[Potion] = _potions.toList

  /** The item decides where it is stored, so no type checks are needed. */
  override def addItem(item: Item): Unit = item.storeIn(this)

  override def addWeapon(weapon: Weapon): Unit = _weapons += weapon

  override def addPotion(potion: Potion): Unit = _potions += potion

  /** A character can attack, move, equip one of its weapons and consume one of its potions. */
  override def actions: List[Action] =
    List(new Attack(), new Move(), new EquipWeapon(weapons), new ConsumePotion(potions))

  override protected def dispatchTo(action: Action, target: Target): Unit =
    action.doByCharacter(this, target)

  override def equipSword(sword: Sword): Unit =
    throw cannotUse(sword)

  override def equipDagger(dagger: Dagger): Unit =
    throw cannotUse(dagger)

  override def equipBow(bow: Bow): Unit =
    throw cannotUse(bow)

  override def equipWand(wand: Wand): Unit =
    throw cannotUse(wand)

  override def equipStaff(staff: Staff): Unit =
    throw cannotUse(staff)

  /** Any character can consume a healing potion: it heals 20% of its maximum health points. */
  override def consumeHealingPotion(potion: HealingPotion): Unit =
    useUp(potion)
    heal(maxHealthPoints * 20 / 100)

  /** Any character can consume a fortitude potion: it increases its defense by 15%. */
  override def consumeFortitudePotion(potion: FortitudePotion): Unit =
    useUp(potion)
    increaseDefense(15)

  override def consumeManaPotion(potion: ManaPotion): Unit =
    throw cannotUse(potion)

  override def consumeMagicStrengthPotion(potion: MagicStrengthPotion): Unit =
    throw cannotUse(potion)

  /** A character can be healed by the Healing spell. */
  override def receiveHealing(caster: WhiteWizard): Unit =
    checkAlive()
    val magicDamage = caster.castSpell(15)
    heal(maxHealthPoints * 20 / 100 + magicDamage / 4)

  /**
   * Puts a weapon of the inventory in the slot. The previous weapon loses its
   * owner and the new one gets this character as owner.
   *
   * @param newWeapon the weapon to equip.
   * @throws InvalidActionException if the weapon is not in the inventory.
   */
  protected def equip(newWeapon: Weapon): Unit =
    if !hasWeapon(newWeapon) then
      throw new InvalidActionException(s"${newWeapon.name} is not in the inventory of $name")
    _weapon.release()
    _weapon = newWeapon
    newWeapon.assignOwner(this)

  /**
   * Removes a potion from the inventory before applying its effect. The list
   * is rebuilt without the potion, so only the first equal potion is removed.
   *
   * @param potion the potion to remove.
   * @throws InvalidActionException if the potion is not in the inventory.
   */
  protected def useUp(potion: Potion): Unit =
    val remaining = mutable.ListBuffer[Potion]()
    var found = false
    for current <- _potions do
      if !found && current == potion then found = true
      else remaining += current
    if !found then
      throw new InvalidActionException(s"${potion.name} is not in the inventory of $name")
    _potions = remaining

  /**
   * Tells whether a weapon is in the inventory.
   *
   * @param searched the weapon to look for.
   * @return `true` if the weapon is in the inventory, `false` otherwise.
   */
  private def hasWeapon(searched: Weapon): Boolean =
    var found = false
    for weapon <- _weapons do
      if weapon == searched then found = true
    found

  /**
   * Creates the exception thrown when this character cannot use an item.
   *
   * @param item the item that cannot be used.
   * @return the exception to throw.
   */
  private def cannotUse(item: Item): InvalidActionException =
    new InvalidActionException(s"$name cannot use ${item.name}")