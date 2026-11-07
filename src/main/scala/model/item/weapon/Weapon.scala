package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A weapon that a character can equip to attack.
 *
 * A weapon has attack points, a weight and may have an owner: the character
 * that has it equipped. Only characters can own weapons, not enemies.
 */
trait Weapon extends Item:

  /** Returns the attack points of this weapon. */
  def attack: Int

  /** Returns the weight of this weapon. */
  def weight: Int

  /** Returns the character that owns this weapon, or None if it has no owner. */
  def owner: Option[GameCharacter]

  /**
   * Returns the damage of an attack made with this weapon.
   *
   * @throws InvalidActionException if this weapon cannot be used to attack.
   */
  def attackDamage: Int

  /**
   * Returns the magic damage that spells cast with this weapon deal.
   *
   * @throws InvalidActionException if this is not a magic weapon.
   */
  def magicDamage: Int

  /**
   * Makes a character the owner of this weapon.
   *
   * @param character the character that equips this weapon.
   */
  def assignOwner(character: GameCharacter): Unit

  /** Leaves this weapon without an owner. */
  def release(): Unit