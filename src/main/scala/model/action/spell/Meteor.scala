package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.AbstractAction
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.character.BlackWizard

/** Meteor, a black magic spell that damages every unit on the target panel. Only black wizards can cast it. */
class Meteor extends AbstractAction("Meteor"), BlackMagicSpell:

  override def doByBlackWizard(wizard: BlackWizard, target: Target): Unit =
    target.receiveMeteor(wizard)