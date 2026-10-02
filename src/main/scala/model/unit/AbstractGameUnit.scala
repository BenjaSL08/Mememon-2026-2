package cl.uchile.dcc
package model.unit

/**
 * Base implementation shared by every [[GameUnit]].
 *
 * Stores the attributes that characters and enemies have in common, so they
 * are written only once. The attributes are private and can only be read
 * through their getters.
 *
 * @param _name name of the unit.
 * @param _healthPoints health points of the unit.
 * @param _defense defense points of the unit.
 * @param _weight weight of the unit.
 */
abstract class AbstractGameUnit(private val _name: String, private val _healthPoints: Int,
                                private val _defense: Int, private val _weight: Int)
  extends GameUnit:

  override def name: String = _name

  override def healthPoints: Int = _healthPoints

  override def defense: Int = _defense

  override def weight: Int = _weight

  override def isAlive: Boolean = healthPoints > 0

  /**
   * Compares this unit with another object.
   *
   * Two units are equal when they belong to the same concrete class (a Knight
   * is never equal to an Archer) and have the same name, health points,
   * defense and weight.
   *
   * @param obj object to compare with.
   * @return `trueif both units are equal, `falseotherwise.
   */
  override def equals(obj: Any): Boolean =
    if getClass == obj.getClass then
      val other = obj.asInstanceOf[AbstractGameUnit]
      name == other.name && healthPoints == other.healthPoints &&
        defense == other.defense && weight == other.weight
    else false

