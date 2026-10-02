package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A weapon that a character can equip to attack.
 *
 * A weapon has attack points, a weight and may have an owner: the character
 * that has it equipped. Only characters can own weapons, since enemies cannot
 * equip them.
 */
trait Weapon extends Item:

  /** Returns the attack points of this weapon. */
  def attack: Int

  /** Returns the weight of this weapon. */
  def weight: Int

  /** Returns the character that owns this weapon, or `None` if it has no owner. */
  def owner: Option[GameCharacter]