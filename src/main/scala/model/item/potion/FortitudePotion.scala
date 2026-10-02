package cl.uchile.dcc
package model.item.potion

/**
 * A fortitude potion, one of the common potions that any character can
 * consume. Its  increases the defense of the character that consumes
 * it by 15%.
 *
 * @param name the name of the potion.
 */
class FortitudePotion(name: String) extends AbstractPotion(name)