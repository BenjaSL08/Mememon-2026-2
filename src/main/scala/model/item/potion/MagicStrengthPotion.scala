package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A magic strength potion, one of the magic potions that only affect magic
 * characters. It increases the magic damage of the next spell by
 * 50%.
 *
 * @param name the name of the potion.
 */
class MagicStrengthPotion(name: String) extends AbstractPotion(name):

  /** The character decides if it can consume a magic strength potion. */
  override def receiveConsume(character: GameCharacter): Unit =
    character.consumeMagicStrengthPotion(this)