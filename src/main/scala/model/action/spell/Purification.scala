package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.AbstractAction
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.character.WhiteWizard

/** Purification, a white magic spell that curses the target enemy. Only white wizards can cast it. */
class Purification extends AbstractAction("Purification"), WhiteMagicSpell:

  override def doByWhiteWizard(wizard: WhiteWizard, target: Target): Unit =
    target.receivePurification(wizard)