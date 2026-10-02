package cl.uchile.dcc
package model.action

import model.item.Item

/**
 * An action that involves items, like equipping a weapon or consuming a
 * potion.
 *
 * Besides its name, it has a list of the items it could use.
 */
trait ItemAction extends Action:

  /** Returns the items this action could use. */
  def items: List[Item]