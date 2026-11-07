package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.item.AbstractItem
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * Base implementation shared by every [[Weapon]].
 *
 * A weapon starts without an owner. A common weapon has no magic attack, so
 * spells cannot be cast with it.
 *
 * @param name the name of the weapon.
 * @param _attack the attack points of the weapon.
 * @param _weight the weight of the weapon.
 */
abstract class AbstractWeapon(name: String, private val _attack: Int, private val _weight: Int)
  extends AbstractItem(name), Weapon:

  /** The character that has this weapon equipped. Private, so only this class can change it. */
  private var _owner: Option[GameCharacter] = None

  override def attack: Int = _attack

  override def weight: Int = _weight

  override def owner: Option[GameCharacter] = _owner

  override def attackDamage: Int = attack

  /** Spells need a magic weapon, so a common weapon cannot cast them. */
  override def magicDamage: Int =
    throw new InvalidActionException(s"Spells cannot be cast with $name, it is not a magic weapon")

  override def assignOwner(character: GameCharacter): Unit = _owner = Some(character)

  override def release(): Unit = _owner = None

  /** A weapon is stored with the weapons of the character. */
  override def storeIn(character: GameCharacter): Unit = character.addWeapon(this)

  /**
   * Compares this weapon with another object.
   *
   * Two weapons are equal when they belong to the same concrete class and have
   * the same name, attack points and weight. The owner is not compared: it is
   * state that changes during the game, not part of what the weapon is.
   *
   * @param obj the object to compare with.
   * @return `true` if both weapons are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    if super.equals(obj) then
      val other = obj.asInstanceOf[AbstractWeapon]
      attack == other.attack && weight == other.weight
    else false