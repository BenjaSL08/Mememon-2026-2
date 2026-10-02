package cl.uchile.dcc
package model.unit

/**
 * An enemy unit.
 *
 * Enemies are different to characters, an enemy has its own attack points and cannot equip
 * weapons or carry items.
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

  /** The action bar of an enemy is its weight. */
  override def maxActionBar: Double = weight

  /**
   * Compares this enemy with another object.
   * @param obj the object to compare with.
   * @return `trueif both enemies are equal, `falseotherwise.
   */
  override def equals(obj: Any): Boolean =
    super.equals(obj) && attack == obj.asInstanceOf[Enemy].attack

