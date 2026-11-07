package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A fortitude potion, one of the common potions that any character can
 * consume. It increases the defense of the character that consumes
 * it by 15%.
 *
 * @param name the name of the potion.
 */
class FortitudePotion(name: String) extends AbstractPotion(name):

  /** The character decides if it can consume a fortitude potion. */
  override def receiveConsume(character: GameCharacter): Unit =
    character.consumeFortitudePotion(this)