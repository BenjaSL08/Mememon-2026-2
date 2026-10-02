package cl.uchile.dcc
package model.unit.character

/**
 * Base implementation shared by every [[MagicCharacter]].
 *
 * @param name the name of the character.
 * @param healthPoints the health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 * @param _mana the mana points of the character.
 */
abstract class AbstractMagicCharacter(name: String, healthPoints: Int, defense: Int,
                                      weight: Int, private val _mana: Int)
  extends AbstractGameCharacter(name, healthPoints, defense, weight), MagicCharacter:

  override def mana: Int = _mana

  /**
   * Compares this magic character with another object.
   *
   * Two magic characters are equal when they share all the attributes of a
   * unit and have the same mana points.
   *
   * @param obj the object to compare with.
   * @return `trueif both characters are equal, `falseotherwise.
   */
  override def equals(obj: Any): Boolean =
    super.equals(obj) && mana == obj.asInstanceOf[AbstractMagicCharacter].mana