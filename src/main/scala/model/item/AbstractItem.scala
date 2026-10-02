package cl.uchile.dcc
package model.item

/**
 * Base implementation shared by every [[Item]].
 *
 * @param _name the name of the item.
 */
abstract class AbstractItem(private val _name: String) extends Item:

  override def name: String = _name

  /**
   * Compares this item with another object.
   *
   * Two items are equal when they belong to the same concrete class (a Sword
   * is never equal to a Dagger) and have the same name.
   *
   * @param obj the object to compare with.
   * @return `true` if both items are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    if getClass == obj.getClass then
      name == obj.asInstanceOf[AbstractItem].name
    else false