package cl.uchile.dcc
package model.unit.character

/**
 * A black wizard, one of the magic character classes, which casts black magic
 * spells.
 *
 * @param name the name of the black mage.
 * @param healthPoints the health points of the black mage.
 * @param defense the defense points of the black mage.
 * @param weight the weight of the black mage.
 * @param mana the mana points of the black mage.
 */
class BlackWizard(name: String, healthPoints: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, healthPoints, defense, weight, mana)