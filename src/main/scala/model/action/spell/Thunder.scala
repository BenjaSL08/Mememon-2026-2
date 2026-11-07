package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.AbstractAction
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.character.BlackWizard

/** Thunder, a black magic spell that damages the target unit. Only black wizards can cast it. */
class Thunder extends AbstractAction("Thunder"), BlackMagicSpell:

  override def doByBlackWizard(wizard: BlackWizard, target: Target): Unit =
    target.receiveThunder(wizard)