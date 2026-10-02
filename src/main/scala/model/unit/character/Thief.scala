package cl.uchile.dcc
package model.unit.character

/**
 * A thief, cannot use magic since it is common.
 *
 * @param name the name of the thief.
 * @param healthPoints the health points of the thief.
 * @param defense the defense points of the thief.
 * @param weight the weight of the thief.
 */
class Thief(name: String, healthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameCharacter(name, healthPoints, defense, weight)