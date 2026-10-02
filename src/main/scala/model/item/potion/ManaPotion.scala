package cl.uchile.dcc
package model.item.potion

/**
 * A mana potion, one of the magic potions that only affect magic
 * characters. Its effect restores 30% of the maximum mana of the character
 * that consumes it.
 *
 * @param name the name of the potion.
 */
class ManaPotion(name: String) extends AbstractPotion(name)