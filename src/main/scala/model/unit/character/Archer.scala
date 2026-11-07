package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.weapon.Bow
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.item.weapon.Wand

/**
 * An archer, a common character that can equip swords, bows and wands.
 *
 * @param name the name of the character.
 * @param maxHealthPoints the maximum health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 */
class Archer(name: String, maxHealthPoints: Int, defense: Int, weight: Int)
  extends AbstractGameCharacter(name, maxHealthPoints, defense, weight):

  override def equipSword(sword: Sword): Unit = equip(sword)

  override def equipBow(bow: Bow): Unit = equip(bow)

  override def equipWand(wand: Wand): Unit = equip(wand)
