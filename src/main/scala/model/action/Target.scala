package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.unit.GameUnit
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.GameCharacter
import cl.uchile.dcc.model.unit.character.WhiteWizard

/**
 * Something that can receive an action: a unit, a panel or an item.
 *
 * Each method is the last step of the dispatch of an action. The action calls
 * it with the unit that does it, and the target applies the effect or rejects
 * the action.
 */
trait Target:

  /**
   * Receives an attack.
   *
   * @param attacker the unit that attacks.
   * @throws InvalidActionException if this target cannot be attacked.
   */
  def receiveAttack(attacker: GameUnit): Unit

  /**
   * Receives a unit that wants to move here.
   *
   * @param unit the unit that moves.
   * @throws InvalidActionException if the unit cannot move to this target.
   */
  def receiveMove(unit: GameUnit): Unit

  /**
   * Is equipped by a character.
   *
   * @param character the character that equips this target.
   * @throws InvalidActionException if the character cannot equip this target.
   */
  def receiveEquip(character: GameCharacter): Unit

  /**
   * Is consumed by a character.
   *
   * @param character the character that consumes this target.
   * @throws InvalidActionException if the character cannot consume this target.
   */
  def receiveConsume(character: GameCharacter): Unit

  /**
   * Receives the Thunder spell.
   *
   * @param caster the black wizard that casts the spell.
   * @throws InvalidActionException if this target cannot receive the spell.
   */
  def receiveThunder(caster: BlackWizard): Unit

  /**
   * Receives the Meteor spell.
   *
   * @param caster the black wizard that casts the spell.
   * @throws InvalidActionException if this target cannot receive the spell.
   */
  def receiveMeteor(caster: BlackWizard): Unit

  /**
   * Receives the Healing spell.
   *
   * @param caster the white wizard that casts the spell.
   * @throws InvalidActionException if this target cannot receive the spell.
   */
  def receiveHealing(caster: WhiteWizard): Unit

  /**
   * Receives the Purification spell.
   *
   * @param caster the white wizard that casts the spell.
   * @throws InvalidActionException if this target cannot receive the spell.
   */
  def receivePurification(caster: WhiteWizard): Unit