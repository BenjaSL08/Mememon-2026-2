package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.item.AbstractItem
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * Base implementation shared by every [[Potion]].
 *
 * @param name the name of the potion.
 */
abstract class AbstractPotion(name: String) extends AbstractItem(name), Potion:

  /** A potion is stored with the potions of the character. */
  override def storeIn(character: GameCharacter): Unit = character.addPotion(this)