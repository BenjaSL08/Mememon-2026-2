package cl.uchile.dcc
package model.turn

import cl.uchile.dcc.model.unit.GameUnit

import scala.collection.mutable

/**
 * Decides which unit gets the turn.
 *
 * Every unit fills its action bar over time. A unit can take its turn once its
 * bar is complete, and the unit that went furthest over its maximum goes first.
 */
class TurnScheduler:

  /** The action bars of the units in battle. Priv so they only change through this class. */
  private var _actionBars = mutable.ListBuffer[ActionBar]()

  /** Returns the units in battle, in the order they were added. */
  def units: List[GameUnit] =
    val result = mutable.ListBuffer[GameUnit]()
    for bar <- _actionBars do result += bar.unit
    result.toList

  /**
   * Adds a unit to the battle, with an empty action bar.
   *
   * @param unit the unit to add.
   */
  def addUnit(unit: GameUnit): Unit = _actionBars += new ActionBar(unit)

  /**
   * Removes a unit from the battle.
   *
   * @param unit the unit to remove.
   */
  def removeUnit(unit: GameUnit): Unit =
    val remaining = mutable.ListBuffer[ActionBar]()
    var removed = false
    for bar <- _actionBars do
      if !removed && bar.unit == unit then removed = true
      else remaining += bar
    _actionBars = remaining

  /**
   * Returns the max of the action bar of a unit.
   *
   * @param unit the unit to check.
   */
  def maxActionBar(unit: GameUnit): Double = unit.maxActionBar

  /**
   * Returns how much of its action bar a unit has filled, or 0 if the unit is
   * not in battle.
   *
   * @param unit the unit to check.
   */
  def currentActionBar(unit: GameUnit): Double =
    val bar = actionBarOf(unit)
    if bar.isDefined then bar.get.current else 0

  /**
   * Empties the action bar of a unit.
   *
   * @param unit the unit whose bar is emptied.
   */
  def resetActionBar(unit: GameUnit): Unit =
    val bar = actionBarOf(unit)
    if bar.isDefined then bar.get.reset()

  /**
   * Fills the action bars of all the units in battle at the same time.
   *
   * @param amount how much to fill each bar.
   */
  def increaseActionBars(amount: Double): Unit =
    for bar <- _actionBars do bar.increase(amount)

  /**
   * Returns true if a unit in battle completed its action bar.
   *
   * @param unit the unit to check.
   */
  def hasCompletedActionBar(unit: GameUnit): Boolean =
    val bar = actionBarOf(unit)
    bar.isDefined && bar.get.isComplete

  /**
   * Returns the units that completed their action bar, from the one that went
   * furthest over its max to the one that went least. Units with the same
   * excess keep the order in which they were added.
   */
  def completedUnits: List[GameUnit] =
    var ordered = mutable.ListBuffer[ActionBar]()
    for bar <- _actionBars do
      if bar.isComplete then ordered = insertByExcess(bar, ordered)
    val result = mutable.ListBuffer[GameUnit]()
    for bar <- ordered do result += bar.unit
    result.toList

  /** Returns the unit that gets the turn, or None if no unit completed its action bar. */
  def nextUnit: Option[GameUnit] =
    var next: Option[GameUnit] = None
    for unit <- completedUnits do
      if next.isEmpty then next = Some(unit)
    next

  /** Returns the action bar of a unit, or None if the unit is not in battle. */
  private def actionBarOf(unit: GameUnit): Option[ActionBar] =
    var found: Option[ActionBar] = None
    for bar <- _actionBars do
      if found.isEmpty && bar.unit == unit then found = Some(bar)
    found

  /**
   * Returns a new list with the bar inserted in its place, keeping the list
   * ordered from the largest to the smallest excess. The bar goes after the
   * bars with the same excess, so ties keep the order in which units were added.
   *
   * @param bar the bar to insert.
   * @param ordered the bars already ordered.
   * @return the ordered bars, including the new one.
   */
  private def insertByExcess(bar: ActionBar,
                             ordered: mutable.ListBuffer[ActionBar]): mutable.ListBuffer[ActionBar] =
    val result = mutable.ListBuffer[ActionBar]()
    var placed = false
    for current <- ordered do
      if !placed && bar.excess > current.excess then
        result += bar
        placed = true
      result += current
    if !placed then result += bar
    result