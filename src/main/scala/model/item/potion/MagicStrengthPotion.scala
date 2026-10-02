package cl.uchile.dcc
package model.item.potion

/**
 * A magic strength potion, one of the magic potions that only affect magic
 * characters. Its effect increases the magic damage of the next spell by
 * 50%.
 *
 * @param name the name of the potion.
 */
class MagicStrengthPotion(name: String) extends AbstractPotion(name)