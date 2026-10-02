package cl.uchile.dcc
package model.action

/**
 * Base implementation shared by every [[Action]].
 *
 * @param _name the name of the action.
 */
abstract class AbstractAction(private val _name: String) extends Action:

  override def name: String = _name