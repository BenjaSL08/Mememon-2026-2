package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.item.weapon.Weapon
import cl.uchile.dcc.model.unit.AbstractGameUnit

import scala.collection.mutable

/**
 * Base implementation shared by every [[GameCharacter]].
 *
 * A character starts with an empty weapon slot and an empty inventory.
 * Equipping a weapon belongs to a later delivery.
 *
 * @param name the name of the character.
 * @param healthPoints the health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 */
abstract class AbstractGameCharacter(name: String, healthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameUnit(name, healthPoints, defense, weight), GameCharacter:

  /** The weapon slot. Private, so only this class can change it. */
  private var _weapon: Option[Weapon] = None

  /** The inventory. Private, so it can only change through [[addItem]] and [[removeItem]]. */
  private val _inventory = mutable.ListBuffer[Item]()

  override def weapon: Option[Weapon] = _weapon

  /** Returns a copy of the inventory, so changing it from outside is not possible. */
  override def inventory: List[Item] = _inventory.toList

  override def addItem(item: Item): Unit = _inventory += item

  override def removeItem(item: Item): Unit = _inventory -= item