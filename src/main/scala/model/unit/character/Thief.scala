package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.weapon.Dagger
import cl.uchile.dcc.model.item.weapon.Sword

/**
 * A thief, a common character that can equip swords and daggers.
 *
 * @param name the name of the character.
 * @param maxHealthPoints the maximum health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 */
class Thief(name: String, maxHealthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameCharacter(name, maxHealthPoints, defense, weight):

  override def equipSword(sword: Sword): Unit = equip(sword)

  override def equipDagger(dagger: Dagger): Unit = equip(dagger)