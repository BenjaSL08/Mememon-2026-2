package cl.uchile.dcc
package model.item.potion

/**
 * A healing potion, one of the common potions that any character can
 * consume. Its effect restores 20% of the maximum health points of the
 * character that consumes it.
 *
 * @param name the name of the potion.
 */
class HealingPotion(name: String) extends AbstractPotion(name)
