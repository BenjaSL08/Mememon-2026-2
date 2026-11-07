package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A mana potion, one of the magic potions that only affect magic
 * characters. It gives 30% of the maximum mana of the character
 * that consumes it.
 *
 * @param name the name of the potion.
 */
class ManaPotion(name: String) extends AbstractPotion(name):

  /** The character decides if it can consume a mana potion. */
  override def receiveConsume(character: GameCharacter): Unit =
    character.consumeManaPotion(this)