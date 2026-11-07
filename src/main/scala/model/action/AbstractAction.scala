package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.GameUnit
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.GameCharacter
import cl.uchile.dcc.model.unit.character.WhiteWizard

/**
 * Base implementation shared by every [[Action]].
 *
 * By default, nobody can do an action: wizards behave like characters,
 * characters and enemies behave like any unit, and a unit is rejected. Each
 * action overrides only the sources that can do it.
 *
 * @param _name the name of the action.
 */
abstract class AbstractAction(private val _name: String) extends Action:

  override def name: String = _name

  override def doByCharacter(character: GameCharacter, target: Target): Unit =
    doByUnit(character, target)

  override def doByBlackWizard(wizard: BlackWizard, target: Target): Unit =
    doByCharacter(wizard, target)

  override def doByWhiteWizard(wizard: WhiteWizard, target: Target): Unit =
    doByCharacter(wizard, target)

  override def doByEnemy(enemy: Enemy, target: Target): Unit =
    doByUnit(enemy, target)

  /**
   * Is done by any kind of unit. By default, units cannot do this action.
   *
   * @param unit the unit that does the action.
   * @param target the target of the action.
   * @throws InvalidActionException if the action cannot be done.
   */
  protected def doByUnit(unit: GameUnit, target: Target): Unit =
    throw new InvalidActionException(s"${unit.name} cannot do $name")