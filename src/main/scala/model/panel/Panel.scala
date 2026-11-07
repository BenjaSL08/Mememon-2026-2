package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.GameUnit

/**
 * A square of the game map where units stand.
 *
 * A panel has coordinates, keeps the units that are on it and knows its
 * neighbors: the adjacent panels a unit could move to from this one. A panel
 * is the target of the actions that happen on a place, like moving.
 */
trait Panel extends Target:

  /** Returns the x coordinate of this panel. */
  def x: Int

  /** Returns the y coordinate of this panel. */
  def y: Int

  /** Returns the units on this panel. */
  def units: List[GameUnit]

  /**
   * Places a unit on this panel. To keep the unit and the panel consistent,
   * use [[GameUnit.placeOn]] instead.
   *
   * @param unit the unit to place.
   */
  def addUnit(unit: GameUnit): Unit

  /**
   * Removes a unit from this panel.
   *
   * @param unit the unit to remove.
   */
  def removeUnit(unit: GameUnit): Unit

  /** Returns the panels adjacent to this one. */
  def neighbors: List[Panel]

  /**
   * Registers a panel as adjacent to this one.
   *
   * @param panel the adjacent panel.
   */
  def addNeighbor(panel: Panel): Unit

  /**
   * Tells whether a panel is adjacent to this one.
   *
   * @param panel the panel to check.
   * @return `true` if the panel is a neighbor of this one, `false` otherwise.
   */
  def isNeighbor(panel: Panel): Boolean