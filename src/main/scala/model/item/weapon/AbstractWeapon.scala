package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.item.AbstractItem
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * Base implementation shared by every [[Weapon]].
 *
 * A weapon starts without an owner. Assigning one is part of equipping the
 * weapon, which belongs to a later delivery.
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

  /**
   * Compares this weapon with another object.
   *
   * Two weapons are equal when they belong to the same concrete class and have
   * the same name, attack points and weight. The owner is not compared it is
   * state that changes during the game, not part of what the weapon is.
   *
   * @param obj the object to compare with.
   * @return `trueif both weapons are equal, `falseotherwise.
   */
  override def equals(obj: Any): Boolean =
    if super.equals(obj) then
      val other = obj.asInstanceOf[AbstractWeapon]
      attack == other.attack && weight == other.weight
    else false