package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.item.weapon.Weapon
import cl.uchile.dcc.model.unit.GameUnit

/**
 * A unit with a character class (Knight, Archer, Thief, Black Wizard or
 * White Wizard).
 *
 * Besides the attributes of every unit, a character has a weapon slot and an
 * inventory where it stores its items.
 */
trait GameCharacter extends GameUnit:

  /** Returns the weapon in this character's slot, or `Noneif the slot is empty. */
  def weapon: Option[Weapon]

  /** Returns the items in this character's inventory, in the order they were added. */
  def inventory: List[Item]

  /**
   * Adds an item to this character's inventory.
   *
   * @param item the item to add.
   */
  def addItem(item: Item): Unit

