package cl.uchile.dcc
package model.player

import cl.uchile.dcc.model.unit.GameUnit

import scala.collection.mutable

/**
 * A player that controls one of the two sides of a match.
 *
 * A player has a single list of units, which can mix characters and enemies,
 * and knows whether it has been defeated.
 */
class Player:

  /** The units of this player. Private, so it can only change through [[addUnit]] ]. */
  private val _units = mutable.ListBuffer[GameUnit]()

  /** Returns a copy of this player's units, in the order they were added. */
  def units: List[GameUnit] = _units.toList

  /**
   * Adds a unit to this player.
   *
   * @param unit the unit to add.
   */
  def addUnit(unit: GameUnit): Unit = _units += unit

  /**
   * Returns `trueif none of this player's units is alive.
   *
   * A player without units counts as defeated, since it has no unit left to
   * play with.
   */
  def isDefeated: Boolean =
    var defeated = true
    for unit <- _units do
      if unit.isAlive then defeated = false
    defeated