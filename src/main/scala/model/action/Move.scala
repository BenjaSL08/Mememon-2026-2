package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.unit.GameUnit

/** The action of moving to an adjacent panel. Any unit can move. */
class Move extends AbstractAction("Move"):

  override protected def doByUnit(unit: GameUnit, target: Target): Unit =
    target.receiveMove(unit)