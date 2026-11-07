package cl.uchile.dcc
package model.item

import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * Something a character can store in its inventory.
 *
 * Items are either weapons or potions. All of them have a name, and they are
 * the targets of the actions that equip or consume them.
 */
trait Item extends Target:

  /** Returns the name of this item. */
  def name: String

  /**
   * Stores this item in the inventory of a character, in the place that
   * matches its type.
   *
   * @param character the character that stores this item.
   */
  def storeIn(character: GameCharacter): Unit