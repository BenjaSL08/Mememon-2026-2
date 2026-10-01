package cl.uchile.dcc
package model.unit

/**
 * An enemy unit.
 *
 * Enemies are different compared to characters, an enemy has its own attack points and cannot equip
 * weapons or carry items, so it has neither a weapon slot nor an inventory.
 *
 * @param name  name of the enemy.
 * @param healthPoints  health points of the enemy.
 * @param defense  defense points of the enemy.
 * @param weight  weight of the enemy.
 * @param _attack  attack points of the enemy.
 */
class Enemy(name: String, healthPoints: Int, defense: Int, weight: Int, private val _attack: Int)
  extends AbstractGameUnit(name, healthPoints, defense, weight):

  /** Returns the attack points of this enemy. */
  def attack: Int = _attack

  /**
   * Compares this enemy with another object.
   *
   * Two enemies are equal when they share all the attributes of a unit and
   * have the same attack points.
   *
   * @param obj the object to compare with.
   * @return `true` if both enemies are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    super.equals(obj) && attack == obj.asInstanceOf[Enemy].attack

  /** Returns a hash code consistent with [[equals]]. */
  override def hashCode: Int = (super.hashCode, attack).hashCode