package cl.uchile.dcc
package model.turn

import cl.uchile.dcc.model.unit.GameUnit

/**
 * The action bar of a unit in battle: how much the unit has filled while
 * waiting for its turn.
 *
 * @param _unit the unit this action bar belongs to.
 */
class ActionBar(private val _unit: GameUnit):

  /** How much of the bar is filled. Priv so it only changes through this class. */
  private var _current: Double = 0

  /** Returns the unit this action bar belongs to. */
  def unit: GameUnit = _unit

  /** Returns how much of the bar is filled. */
  def current: Double = _current

  /** Returns the maximum of the bar, which depends on the unit. */
  def max: Double = _unit.maxActionBar

  /**
   * Fills the bar by the given amount.
   *
   * @param amount how much to fill.
   */
  def increase(amount: Double): Unit = _current = _current + amount

  /** Empties the bar. */
  def reset(): Unit = _current = 0

  /** Returns true if the bar reached its max. */
  def isComplete: Boolean = _current >= max

  /** Returns how much the bar went over its max. */
  def excess: Double = _current - max