package cl.uchile.dcc
package model.unit.character

/**
 * An archer, one of the common character classes, which cannot use magic.
 *
 * @param name the name of the archer.
 * @param healthPoints the health points of the archer.
 * @param defense the defense points of the archer.
 * @param weight the weight of the archer.
 */
class Archer(name: String, healthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameCharacter(name, healthPoints, defense, weight)

