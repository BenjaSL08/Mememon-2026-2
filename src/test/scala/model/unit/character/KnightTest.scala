package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.{Enemy, GameUnit}

class KnightTest extends munit.FunSuite:
  var knight: Knight = null

  override def beforeEach(context: BeforeEach): Unit =
    knight = new Knight("Arthur", 100, 20, 80)

  test("A knight has a name, health points, defense and weight"):
    assertEquals(knight.name, "Arthur")
    assertEquals(knight.healthPoints, 100)
    assertEquals(knight.defense, 20)
    assertEquals(knight.weight, 80)

  test("A knight with health points is alive"):
    assertEquals(knight.isAlive, true)

  test("A knight with zero health points is not alive"):
    assertEquals(new Knight("Arthur", 0, 20, 80).isAlive, false)

  test("A knight starts with an empty weapon slot"):
    assertEquals(knight.weapon, None)

  test("A knight starts with an empty inventory"):
    assertEquals(knight.inventory, List())

  test("A knight can store items in its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    knight.addItem(potion)
    knight.addItem(sword)
    assertEquals(knight.inventory, List(potion, sword))

  test("A knight can remove an item from its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    knight.addItem(potion)
    knight.addItem(sword)
    knight.removeItem(potion)
    assertEquals(knight.inventory, List(sword))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = knight.inventory
    knight.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("Two knights with the same attributes are equal"):
    val other = new Knight("Arthur", 100, 20, 80)
    assertEquals(knight, other)
    assertEquals(other, knight)

  test("Two knights with a different attribute are not equal"):
    assertNotEquals(knight, new Knight("Arthur II", 100, 20, 80))
    assertNotEquals(knight, new Knight("Arthur", 90, 20, 80))
    assertNotEquals(knight, new Knight("Arthur", 100, 23, 80))
    assertNotEquals(knight, new Knight("Arthur", 100, 20, 85))

  test("A knight is not equal to an archer with the same attributes"):
    val other: GameUnit = new Archer("Arthur", 100, 20, 80)
    assertNotEquals(other, knight)

  test("A knight is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Arthur", 100, 20, 80, 10)
    assertNotEquals(enemy, knight)