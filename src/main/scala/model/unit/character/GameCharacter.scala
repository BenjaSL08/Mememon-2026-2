package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.item.potion.FortitudePotion
import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.potion.MagicStrengthPotion
import cl.uchile.dcc.model.item.potion.ManaPotion
import cl.uchile.dcc.model.item.potion.Potion
import cl.uchile.dcc.model.item.weapon.Bow
import cl.uchile.dcc.model.item.weapon.Dagger
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.item.weapon.Wand
import cl.uchile.dcc.model.item.weapon.Weapon
import cl.uchile.dcc.model.unit.GameUnit

/**
 * A unit with a character class (Knight, Archer, Thief, Black Wizard or
 * White Wizard).
 *
 * Besides the attributes of every unit, a character has a weapon slot and an
 * inventory with weapons and potions. The equip and consume methods are the
 * second step of a double dispatch: a weapon or a potion calls the one that
 * matches its own type, and each character class decides what it accepts.
 */
trait GameCharacter extends GameUnit:

  /** Returns the weapon in this character's slot, which is [[NoWeapon]] if it has none. */
  def weapon: Weapon

  /** Returns the items in this character's inventory: first its weapons, then its potions. */
  def inventory: List[Item]

  /** Returns the weapons in this character's inventory, in the order they were added. */
  def weapons: List[Weapon]

  /** Returns the potions in this character's inventory, in the order they were added. */
  def potions: List[Potion]

  /**
   * Adds an item to this character's inventory.
   *
   * @param item the item to add.
   */
  def addItem(item: Item): Unit

  /**
   * Adds a weapon to this character's inventory.
   *
   * @param weapon the weapon to add.
   */
  def addWeapon(weapon: Weapon): Unit

  /**
   * Adds a potion to this character's inventory.
   *
   * @param potion the potion to add.
   */
  def addPotion(potion: Potion): Unit

  /**
   * Equips a sword from the inventory.
   *
   * @param sword the sword to equip.
   * @throws InvalidActionException if this character cannot equip it.
   */
  def equipSword(sword: Sword): Unit

  /**
   * Equips a dagger from the inventory.
   *
   * @param dagger the dagger to equip.
   * @throws InvalidActionException if this character cannot equip it.
   */
  def equipDagger(dagger: Dagger): Unit

  /**
   * Equips a bow from the inventory.
   *
   * @param bow the bow to equip.
   * @throws InvalidActionException if this character cannot equip it.
   */
  def equipBow(bow: Bow): Unit

  /**
   * Equips a wand from the inventory.
   *
   * @param wand the wand to equip.
   * @throws InvalidActionException if this character cannot equip it.
   */
  def equipWand(wand: Wand): Unit

  /**
   * Equips a staff from the inventory.
   *
   * @param staff the staff to equip.
   * @throws InvalidActionException if this character cannot equip it.
   */
  def equipStaff(staff: Staff): Unit

  /**
   * Consumes a healing potion from the inventory.
   *
   * @param potion the potion to consume.
   * @throws InvalidActionException if this character cannot consume it.
   */
  def consumeHealingPotion(potion: HealingPotion): Unit

  /**
   * Consumes a fortitude potion from the inventory.
   *
   * @param potion the potion to consume.
   * @throws InvalidActionException if this character cannot consume it.
   */
  def consumeFortitudePotion(potion: FortitudePotion): Unit

  /**
   * Consumes a mana potion from the inventory.
   *
   * @param potion the potion to consume.
   * @throws InvalidActionException if this character cannot consume it.
   */
  def consumeManaPotion(potion: ManaPotion): Unit

  /**
   * Consumes a magic strength potion from the inventory.
   *
   * @param potion the potion to consume.
   * @throws InvalidActionException if this character cannot consume it.
   */
  def consumeMagicStrengthPotion(potion: MagicStrengthPotion): Unit
