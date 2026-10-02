package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.potion.ManaPotion

class ConsumePotionTest extends munit.FunSuite:

  test("Consuming a potion is an action named Consume potion"):
    val action: Action = new ConsumePotion(List())
    assertEquals(action.name, "Consume potion")

  test("Consuming a potion has the list of potions it could consume"):
    val healing = new HealingPotion("Life Potion")
    val mana = new ManaPotion("Mana Potion")
    val action: ItemAction = new ConsumePotion(List(healing, mana))
    assertEquals(action.items, List(healing, mana))

  test("Consuming a potion can have no potions to consume"):
    val action: ItemAction = new ConsumePotion(List())
    assertEquals(action.items, List())