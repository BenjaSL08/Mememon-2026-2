package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.AbstractAction
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.character.WhiteWizard

/** Healing, a white magic spell that heals the target character. Only white wizards can cast it. */
class Healing extends AbstractAction("Healing"), WhiteMagicSpell:

  override def doByWhiteWizard(wizard: WhiteWizard, target: Target): Unit =
    target.receiveHealing(wizard)