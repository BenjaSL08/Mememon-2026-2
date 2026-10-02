package cl.uchile.dcc
package model.action

/**
 * Something a unit can do during its turn.
 *
 * Every action has a name. Who performs an action and who receives it belong
 * to a later delivery.
 */
trait Action:

  /** Returns the name of this action. */
  def name: String