package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.action.AbstractTarget
import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.unit.GameUnit
import cl.uchile.dcc.model.unit.character.BlackWizard

import scala.collection.mutable

/**
 * A panel of the map, with its own coordinates.
 *
 * A unit can move here from an adjacent panel, and the Meteor spell can hit
 * every unit on it.
 *
 * @param _x the x coordinate of the panel.
 * @param _y the y coordinate of the panel.
 */
class GridPanel(private val _x: Int, private val _y: Int) extends AbstractTarget, Panel:

  /** The units on this panel. Private so it only changes through [[addUnit]] and [[removeUnit]]. */
  private var _units = mutable.ListBuffer[GameUnit]()

  /** The adjacent panels. Private so it only changes through [[addNeighbor]]. */
  private val _neighbors = mutable.ListBuffer[Panel]()

  override def x: Int = _x

  override def y: Int = _y

  /** Returns a copy of the units on this panel. */
  override def units: List[GameUnit] = _units.toList

  override def addUnit(unit: GameUnit): Unit = _units += unit

  /** Rebuilds the list of units without the removed one. */
  override def removeUnit(unit: GameUnit): Unit =
    val remaining = mutable.ListBuffer[GameUnit]()
    var removed = false
    for current <- _units do
      if !removed && current == unit then removed = true
      else remaining += current
    _units = remaining

  /** Returns a copy of the panels adjacent to this one. */
  override def neighbors: List[Panel] = _neighbors.toList

  override def addNeighbor(panel: Panel): Unit = _neighbors += panel

  override def isNeighbor(panel: Panel): Boolean =
    var found = false
    for neighbor <- _neighbors do
      if neighbor == panel then found = true
    found

  /** A unit can move here only from an adjacent panel. */
  override def receiveMove(unit: GameUnit): Unit =
    if !isNeighbor(unit.panel) then
      throw new InvalidActionException(s"${unit.name} cannot move to a panel that is not adjacent")
    unit.placeOn(this)

  /** Meteor damages every unit on this panel with the magic damage of the caster. */
  override def receiveMeteor(caster: BlackWizard): Unit =
    val magicDamage = caster.castSpell(50)
    for unit <- units do unit.receiveDamage(magicDamage)