package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.action.AbstractTarget
import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * The empty weapon slot.
 *
 * A character without a weapon holds this object instead of None, so nobody
 * has to check whether the slot is empty: this object answers like any weapon.
 * It weighs nothing and cannot be used to attack nor to cast spells. There
 * is only one empty slot, so it is an `object`.
 */
object NoWeapon extends AbstractTarget, Weapon:

  override def name: String = "No weapon"

  override def attack: Int = 0

  override def weight: Int = 0

  override def owner: Option[GameCharacter] = None

  /** A character without a weapon cannot attack. */
  override def attackDamage: Int =
    throw new InvalidActionException("Cannot attack without a weapon")

  /** A character without a weapon cannot cast spells. */
  override def magicDamage: Int =
    throw new InvalidActionException("Cannot cast spells without a magic weapon")

  /** The empty slot has no owner, so assigning one does nothing. */
  override def assignOwner(character: GameCharacter): Unit = ()

  /** The empty slot has no owner, so releasing it does nothing. */
  override def release(): Unit = ()

  /** The empty slot is not a real item, so it is never stored. */
  override def storeIn(character: GameCharacter): Unit = ()