package cl.uchile.dcc
package model.action

/**
 * Thrown when an action cant be done: the unit cant perform it, the
 * target cant receive it, or a requirement of the action is not met.
 *
 * @param message the reason why the action failed.
 */
class InvalidActionException(message: String) extends Exception(message)