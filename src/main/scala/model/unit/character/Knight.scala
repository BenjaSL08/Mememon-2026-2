package cl.uchile.dcc
package model.unit.character

/**
 * A knight, one of the common character classes, which cannot use magic.
 *
 * @param name the name of the knight.
 * @param healthPoints the health points of the knight.
 * @param defense the defense points of the knight.
 * @param weight the weight of the knight.
 */
class Knight(name: String, healthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameCharacter(name, healthPoints, defense, weight)