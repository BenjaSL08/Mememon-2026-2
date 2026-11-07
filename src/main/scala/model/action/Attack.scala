package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.unit.GameUnit

/** The action of attacking a unit. Any unit can attack. */
class Attack extends AbstractAction("Attack"):

  override protected def doByUnit(unit: GameUnit, target: Target): Unit =
    target.receiveAttack(unit)