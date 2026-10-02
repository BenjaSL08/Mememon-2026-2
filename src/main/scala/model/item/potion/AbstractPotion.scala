package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.item.AbstractItem

/**
 * Base implementation shared by every [[Potion]].
 *
 * @param name the name of the potion.
 */
abstract class AbstractPotion(name: String) extends AbstractItem(name), Potion