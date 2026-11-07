package cl.uchile.dcc
package controller

import cl.uchile.dcc.model.action.Action
import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.turn.TurnScheduler
import cl.uchile.dcc.model.unit.GameUnit

/**
 * Main controller of the game.
 *
 * Connects the units of the model with the [[TurnScheduler]]: it decides which
 * unit plays each turn, lets that unit do one of its actions, and only ends
 * the turn when the action succeeds. For now the game is an infinite loop of
 * turns: how it ends belongs to a later delivery.
 */
class GameController:

  /** Decides which unit gets each turn. */
  private val scheduler = new TurnScheduler()

  /** The unit playing the current turn, or None between turns. */
  private var _currentUnit: Option[GameUnit] = None

  /** The reason why the last action failed, or None if it succeeded. */
  private var _lastError: Option[String] = None

  /** How much the action bars are filled at each step while waiting for a turn. */
  private val step: Double = 10

  /**
   * Adds a unit to the game.
   *
   * @param unit the unit to add.
   */
  def addUnit(unit: GameUnit): Unit = scheduler.addUnit(unit)

  /** Returns the units in the game, in the order they were added. */
  def units: List[GameUnit] = scheduler.units

  /** Returns the reason why the last action failed, or None if it succeeded. */
  def lastError: Option[String] = _lastError

  /** Returns the unit playing the current turn, or None between turns. */
  def currentUnit: Option[GameUnit] = _currentUnit

  /**
   * Decides which unit plays the next turn: fills the action bars until one is
   * complete, and gives the turn to the unit that went furthest over its max.
   * Without units in the game, nobody gets the turn.
   *
   * @return the unit that plays the next turn, or None if there are no units.
   */
  def nextTurn(): Option[GameUnit] =
    if units.length > 0 then
      while scheduler.nextUnit.isEmpty do scheduler.increaseActionBars(step)
      _currentUnit = scheduler.nextUnit
    _currentUnit

  /**
   * Returns the actions a unit can do.
   *
   * @param unit the unit to check.
   */
  def actionsOf(unit: GameUnit): List[Action] = unit.actions

  /**
   * Makes the unit of the current turn do an action on a target. If the action
   * succeeds, the turn ends: the action bar of the unit is emptied and the
   * defeated units leave the game. If it fails, the turn does not change, and
   * the reason is printed and kept in [[lastError]].
   *
   * @param action the action to do.
   * @param target the target of the action.
   * @return `true` if the action was done, `false` otherwise.
   */
  def executeAction(action: Action, target: Target): Boolean =
    if _currentUnit.isEmpty then
      _lastError = Some("No unit has the turn")
      false
    else
      try
        _currentUnit.get.doAction(action, target)
        _lastError = None
        endTurn()
        true
      catch
        case e: InvalidActionException =>
          _lastError = Some(e.getMessage)
          println(s"Action failed: ${e.getMessage}")
          false

  /** Restarts the turns: every action bar is emptied and no unit has the turn. */
  def reset(): Unit =
    for unit <- units do scheduler.resetActionBar(unit)
    _currentUnit = None

  /** Ends the current turn and removes the defeated units from the game. */
  private def endTurn(): Unit =
    scheduler.resetActionBar(_currentUnit.get)
    for unit <- units do
      if !unit.isAlive then scheduler.removeUnit(unit)
    _currentUnit = None