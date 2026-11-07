package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.GameCharacter
import cl.uchile.dcc.model.unit.character.WhiteWizard

/**
 * Something a unit can do during its turn.
 *
 * Every action has a name. The methods below are the second step of the
 * dispatch: the source calls the one that matches its own kind, and the action
 * then tells the [[Target]] what to receive.
 */
trait Action:

  /** Returns the name of this action. */
  def name: String

  /**
   * Is done by a common character.
   *
   * @param character the character that does the action.
   * @param target the target of the action.
   * @throws InvalidActionException if the action cannot be done.
   */
  def doByCharacter(character: GameCharacter, target: Target): Unit

  /**
   * Is done by a black wizard.
   *
   * @param wizard the black wizard that does the action.
   * @param target the target of the action.
   * @throws InvalidActionException if the action cannot be done.
   */
  def doByBlackWizard(wizard: BlackWizard, target: Target): Unit

  /**
   * Is done by a white wizard.
   *
   * @param wizard the white wizard that does the action.
   * @param target the target of the action.
   * @throws InvalidActionException if the action cannot be done.
   */
  def doByWhiteWizard(wizard: WhiteWizard, target: Target): Unit

  /**
   * Is done by an enemy.
   *
   * @param enemy the enemy that does the action.
   * @param target the target of the action.
   * @throws InvalidActionException if the action cannot be done.
   */
  def doByEnemy(enemy: Enemy, target: Target): Unit