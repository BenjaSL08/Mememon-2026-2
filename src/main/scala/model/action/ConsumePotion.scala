package cl.uchile.dcc
package model.action

import model.item.Item
import model.item.potion.Potion

/**
 * The action of consuming a potion.
 *
 * @param _potions the potions this action could consume.
 */
class ConsumePotion(private val _potions: List[Potion])
  extends AbstractAction("Consume potion"), ItemAction:

  override def items: List[Item] = _potions