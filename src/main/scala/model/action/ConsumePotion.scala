package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.item.potion.Potion
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * The action of consuming a potion. Only characters can do it.
 *
 * @param _potions the potions this action could consume.
 */
class ConsumePotion(private val _potions: List[Potion])
  extends AbstractAction("Consume potion"), ItemAction:

  override def items: List[Item] = _potions

  override def doByCharacter(character: GameCharacter, target: Target): Unit =
    target.receiveConsume(character)