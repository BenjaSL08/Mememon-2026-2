package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A healing potion, one of the common potions that any character can
 * consume. It heals 20% of the maximum health points of the
 * character that consumes it.
 *
 * @param name the name of the potion.
 */
class HealingPotion(name: String) extends AbstractPotion(name):

  /** The character decides if it can consume a healing potion. */
  override def receiveConsume(character: GameCharacter): Unit =
    character.consumeHealingPotion(this)