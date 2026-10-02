package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.unit.GameUnit

import scala.collection.mutable

/**
 * A square of the game map where units are showed.
 *
 * A panel has coordinates, keeps the units that are on it and knows its
 * neighbors: the adjacent panels a unit could move to from this one.
 *
 * @param _x the x coordinate of the panel.
 * @param _y the y coordinate of the panel.
 */
class Panel(private val _x: Int, private val _y: Int):

  /** The units on this panel. Priv so it can only change through [[addUnit]]. */
  private val _units = mutable.ListBuffer[GameUnit]()

  /** The adjacent panels. Priv so it can only change through [[addNeighbor]]. */
  private val _neighbors = mutable.ListBuffer[Panel]()

  /** Returns the x coordinate of this panel. */
  def x: Int = _x

  /** Returns the y coordinate of this panel. */
  def y: Int = _y

  /** Returns a copy of the units on this panel, */
  def units: List[GameUnit] = _units.toList

  /**
   * Places a unit on this panel.
   *
   * @param unit the unit to place.
   */
  def addUnit(unit: GameUnit): Unit = _units += unit

  /** Returns a copy of the panels adjacent to this one. */
  def neighbors: List[Panel] = _neighbors.toList

  /**
   * Registers a panel as adjacent to this one.
   *
   * @param panel the adjacent panel.
   */
  def addNeighbor(panel: Panel): Unit = _neighbors += panel