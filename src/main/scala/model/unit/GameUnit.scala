package cl.uchile.dcc
package model.unit

import cl.uchile.dcc.model.action.Source
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.panel.Panel

/**
 * A unit that takes part in a match.
 *
 * Units are the pieces that a player controls: characters and enemies. Every
 * unit has a name, health points, defense and weight, stands on a panel, and
 * can tell whether it is still alive. A unit is both a [[Source]] (it does
 * actions) and a [[Target]] (it receives them).
 */
trait GameUnit extends Source, Target:

  /** Returns the maximum health points of this unit. */
  def maxHealthPoints: Int

  /** Returns the current health points of this unit. */
  def healthPoints: Int

  /** Returns the current defense points of this unit. */
  def defense: Int

  /** Returns the weight of this unit, which determines how fast it gets its turn. */
  def weight: Int

  /** Returns `true` if this unit has more than zero health points. */
  def isAlive: Boolean

  /**
   * Returns the max of this unit's action bar: how much it must fill
   * before getting its turn.
   */
  def maxActionBar: Double

  /**
   * Returns the damage this unit deals when it attacks, before the defense of
   * the target is subtracted.
   *
   * @throws InvalidActionException if this unit cannot attack.
   */
  def attackDamage: Int

  /** Returns the panel this unit stands on. */
  def panel: Panel

  /**
   * Places this unit on a panel, leaving the panel it was on.
   *
   * @param panel the new panel of this unit.
   */
  def placeOn(panel: Panel): Unit

  /**
   * Lowers the health points of this unit. Health points never go below
   * zero, and a negative damage does nothing.
   *
   * @param damage the amount of health points to lose.
   */
  def receiveDamage(damage: Int): Unit

  /**
   * Restores health points of this unit, up to its maximum health points.
   *
   * @param amount the amount of health points to restore.
   */
  def heal(amount: Int): Unit

