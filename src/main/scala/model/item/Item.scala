package cl.uchile.dcc
package model.item

/**
 * Something a character can store in its inventory.
 *
 * Items are either weapons or potions. All of them have a name.
 */
trait Item:

  /** Returns the name of this item. */
  def name: String