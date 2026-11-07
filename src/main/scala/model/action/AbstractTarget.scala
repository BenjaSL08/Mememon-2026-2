package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.unit.GameUnit
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.GameCharacter
import cl.uchile.dcc.model.unit.character.WhiteWizard

/**
 * Base implementation shared by every [[Target]].
 *
 * By default, a target rejects every action. Each kind of target overrides
 * only the actions it can receive, like the base armor of the double dispatch
 * example of the course.
 */
abstract class AbstractTarget extends Target:

  override def receiveAttack(attacker: GameUnit): Unit =
    throw rejection("an attack")

  override def receiveMove(unit: GameUnit): Unit =
    throw rejection("a unit moving here")

  override def receiveEquip(character: GameCharacter): Unit =
    throw rejection("being equipped")

  override def receiveConsume(character: GameCharacter): Unit =
    throw rejection("being consumed")

  override def receiveThunder(caster: BlackWizard): Unit =
    throw rejection("Thunder")

  override def receiveMeteor(caster: BlackWizard): Unit =
    throw rejection("Meteor")

  override def receiveHealing(caster: WhiteWizard): Unit =
    throw rejection("Healing")

  override def receivePurification(caster: WhiteWizard): Unit =
    throw rejection("Purification")

  /**
   * Creates the exception thrown when this target cannot receive an action.
   *
   * @param action a description of the action.
   * @return the exception to throw.
   */
  protected def rejection(action: String): InvalidActionException =
    new InvalidActionException(s"This target cannot receive $action")