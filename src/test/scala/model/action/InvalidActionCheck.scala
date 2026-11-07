package cl.uchile.dcc
package model.action

/** Helper for the tests that check that an action fails. */
object InvalidActionCheck:

  /**
   * Runs some code and tells whether it threw an [[InvalidActionException]].
   *
   * @param code the code to run.
   * @return `true` if the code threw an [[InvalidActionException]], `false` otherwise.
   */
  def fails(code: () => Unit): Boolean =
    var failed = false
    try code()
    catch case e: InvalidActionException => failed = true
    failed