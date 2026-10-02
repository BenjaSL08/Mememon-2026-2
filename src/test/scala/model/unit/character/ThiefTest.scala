package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.{Enemy, GameUnit}

class ThiefTest extends munit.FunSuite:
  var thief: Thief = null

  override def beforeEach(context: BeforeEach): Unit =
    thief = new Thief("Locke", 70, 8, 40)

  test("A thief has a name, health points, defense and weight"):
    assertEquals(thief.name, "Locke")
    assertEquals(thief.healthPoints, 70)
    assertEquals(thief.defense, 8)
    assertEquals(thief.weight, 40)

  test("A thief with health points is alive"):
    assertEquals(thief.isAlive, true)

  test("A thief with zero health points is not alive"):
    assertEquals(new Thief("Locke", 0, 8, 40).isAlive, false)

  test("A thief starts with an empty weapon slot"):
    assertEquals(thief.weapon, None)

  test("A thief starts with an empty inventory"):
    assertEquals(thief.inventory, List())

  test("A thief can store items in its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    thief.addItem(potion)
    thief.addItem(sword)
    assertEquals(thief.inventory, List(potion, sword))

  test("A thief can remove an item from its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    thief.addItem(potion)
    thief.addItem(sword)
    thief.removeItem(potion)
    assertEquals(thief.inventory, List(sword))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = thief.inventory
    thief.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("Two thieves with the same attributes are equal"):
    val other = new Thief("Locke", 70, 8, 40)
    assertEquals(thief, other)
    assertEquals(other, thief)

  test("Two thieves with a different attribute are not equal"):
    assertNotEquals(thief, new Thief("Locke II", 70, 8, 40))
    assertNotEquals(thief, new Thief("Locke", 60, 8, 40))
    assertNotEquals(thief, new Thief("Locke", 70, 11, 40))
    assertNotEquals(thief, new Thief("Locke", 70, 8, 45))

  test("A thief is not equal to a knight with the same attributes"):
    val other: GameUnit = new Knight("Locke", 70, 8, 40)
    assertNotEquals(other, thief)

  test("A thief is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Locke", 70, 8, 40, 10)
    assertNotEquals(enemy, thief)