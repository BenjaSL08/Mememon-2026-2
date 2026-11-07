package cl.uchile.dcc
package model.unit

import cl.uchile.dcc.model.action.AbstractTarget
import cl.uchile.dcc.model.action.Action
import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.panel.NoPanel
import cl.uchile.dcc.model.panel.Panel
import cl.uchile.dcc.model.unit.character.BlackWizard

/**
 * Base implementation shared by every [[GameUnit]].
 *
 * Stores the attributes that characters and enemies have in common, so they
 * are written only once. A unit starts with its maximum health points and on
 * no panel. Every unit can be attacked and hit by Thunder. A defeated unit
 * cannot do actions nor receive them.
 *
 * @param _name name of the unit.
 * @param _maxHealthPoints maximum health points of the unit.
 * @param _baseDefense defense points the unit starts with.
 * @param _weight weight of the unit.
 */
abstract class AbstractGameUnit(private val _name: String, private val _maxHealthPoints: Int, private val _baseDefense: Int, private val _weight: Int)
  extends AbstractTarget, GameUnit:

  /** The current health points. Private so they only change through damage and healing. */
  private var _healthPoints: Int = _maxHealthPoints

  /** The current defense points. Private so they only change through [[increaseDefense]]. */
  private var _defense: Int = _baseDefense

  /** The panel this unit stands on. Starts as [[NoPanel]] until the unit is placed. */
  private var _panel: Panel = NoPanel

  override def name: String = _name

  override def maxHealthPoints: Int = _maxHealthPoints

  override def healthPoints: Int = _healthPoints

  override def defense: Int = _defense

  override def weight: Int = _weight

  override def isAlive: Boolean = healthPoints > 0

  override def panel: Panel = _panel

  override def placeOn(panel: Panel): Unit =
    _panel.removeUnit(this)
    _panel = panel
    panel.addUnit(this)

  override def receiveDamage(damage: Int): Unit =
    _healthPoints = Math.max(0, _healthPoints - Math.max(0, damage))

  override def heal(amount: Int): Unit =
    _healthPoints = Math.min(maxHealthPoints, _healthPoints + amount)

  /**
   * Increases the defense of this unit.
   *
   * @param percentage the percentage of the current defense to add.
   */
  protected def increaseDefense(percentage: Int): Unit =
    _defense += _defense * percentage / 100

  /**
   * Does an action on a target, only if this unit is still alive. The kind of
   * unit decides how the action is dispatched, in [[dispatchTo]].
   */
  override def doAction(action: Action, target: Target): Unit =
    if !isAlive then
      throw new InvalidActionException(s"$name is defeated and cannot do actions")
    dispatchTo(action, target)

  /**
   * Tells the action which kind of unit does it: the first step of the dispatch.
   *
   * @param action the action to do.
   * @param target the target of the action.
   */
  protected def dispatchTo(action: Action, target: Target): Unit

  /**
   * Checks that this unit can still receive actions.
   *
   * @throws InvalidActionException if this unit is defeated.
   */
  protected def checkAlive(): Unit =
    if !isAlive then
      throw new InvalidActionException(s"$name is defeated and cannot receive actions")

  override def findAction(name: String): Option[Action] =
    var found: Option[Action] = None
    for action <- actions do
      if found.isEmpty && action.name == name then found = Some(action)
    found

  /** Any unit can be attacked: it loses the damage of the attacker minus its defense. */
  override def receiveAttack(attacker: GameUnit): Unit =
    checkAlive()
    receiveDamage(attacker.attackDamage - defense)

  /** Any unit can be hit by Thunder: it loses the magic damage of the caster. */
  override def receiveThunder(caster: BlackWizard): Unit =
    checkAlive()
    receiveDamage(caster.castSpell(20))

  /**
   * Compares this unit with another object.
   *
   * Two units are equal when they belong to the same concrete class and have
   * the same name, maximum health points, base defense and weight. Current
   * health points, defense and panel are not compared: they are state that
   * changes during the game.
   *
   * @param obj object to compare with.
   * @return `true` if both units are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    if getClass == obj.getClass then
      val other = obj.asInstanceOf[AbstractGameUnit]
      name == other.name && maxHealthPoints == other.maxHealthPoints &&
        _baseDefense == other._baseDefense && weight == other.weight
    else false