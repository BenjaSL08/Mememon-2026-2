package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.action.AbstractTarget
import cl.uchile.dcc.model.unit.GameUnit

/**
 * The place of a unit that is not on the map yet.
 *
 * A new unit stands here instead of on None, so nobody has to check whether
 * the unit was placed. This panel holds no units and has no neighbors, so a
 * unit on it cannot move anywhere until it is placed on a real panel. There
 * is only one such place, so it is an `object`.
 */
object NoPanel extends AbstractTarget, Panel:

  override def x: Int = 0

  override def y: Int = 0

  override def units: List[GameUnit] = List()

  /** This panel never holds units. */
  override def addUnit(unit: GameUnit): Unit = ()

  /** This panel never holds units, so there is nothing to remove. */
  override def removeUnit(unit: GameUnit): Unit = ()

  override def neighbors: List[Panel] = List()

  /** This panel never has neighbors. */
  override def addNeighbor(panel: Panel): Unit = ()

  override def isNeighbor(panel: Panel): Boolean = false