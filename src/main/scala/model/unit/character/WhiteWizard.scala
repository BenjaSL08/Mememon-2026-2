package cl.uchile.dcc
package model.unit.character

/**
 * A white wizard, one of the magic character classes, which casts white magic
 * spells.
 *
 * @param name the name of the white mage.
 * @param healthPoints the health points of the white mage.
 * @param defense the defense points of the white mage.
 * @param weight the weight of the white mage.
 * @param mana the mana points of the white mage.
 */
class WhiteWizard(name: String, healthPoints: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, healthPoints, defense, weight, mana)
