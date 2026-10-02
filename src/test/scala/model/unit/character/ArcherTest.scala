package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.GameUnit

class ArcherTest extends munit.FunSuite:
  var archer: Archer = null

  override def beforeEach(context: BeforeEach): Unit =
    archer = new Archer("Ashe", 80, 10, 50)

  test("An archer has a name, health points, defense and weight"):
    assertEquals(archer.name, "Ashe")
    assertEquals(archer.healthPoints, 80)
    assertEquals(archer.defense, 10)
    assertEquals(archer.weight, 50)

  test("An archer with health points is alive"):
    assertEquals(archer.isAlive, true)

  test("An archer with zero health points is not alive"):
    assertEquals(new Archer("Ashe", 0, 10, 50).isAlive, false)

  test("An archer starts with an empty weapon slot"):
    assertEquals(archer.weapon, None)

  test("An archer starts with an empty inventory"):
    assertEquals(archer.inventory, List())

  test("An archer can store items in its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Diamond Sword", 30, 10)
    archer.addItem(potion)
    archer.addItem(sword)
    assertEquals(archer.inventory, List(potion, sword))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = archer.inventory
    archer.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("Two archers with the same attributes are equal"):
    val other = new Archer("Ashe", 80, 10, 50)
    assertEquals(archer, other)
    assertEquals(other, archer)

  test("An archer is not equal to a thief with the same attributes"):
    val other: GameUnit = new Thief("Twitch", 80, 10, 50)
    assertNotEquals(other, archer)

  test("An archer is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Ashe", 80, 10, 50, 10)
    assertNotEquals(enemy, archer)