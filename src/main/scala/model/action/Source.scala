package cl.uchile.dcc
package model.action

/**
 * Something that can do actions during its turn.
 *
 * A source has a name, the actions it can perform, a way to find one of them
 * by its name, and can do an action on a [[Target]].
 */
trait Source:

  /** Returns the name of this source. */
  def name: String

  /** Returns the actions this source can perform. */
  def actions: List[Action]

  /**
   * Finds one of the actions of this source by its name.
   *
   * @param name the name of the action.
   * @return the action with that name, or None if this source does not have it.
   */
  def findAction(name: String): Option[Action]

  /**
   * Does an action on a target.
   *
   * This is the first step of the dispatch: the source tells the action what
   * kind of source it is.
   *
   * @param action the action to do.
   * @param target the target that receives the action.
   * @throws InvalidActionException if the action cannot be done.
   */
  def doAction(action: Action, target: Target): Unit