package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.GameUnit

class WhiteWizardTest extends munit.FunSuite:
  var whiteWizard: WhiteWizard = null

  override def beforeEach(context: BeforeEach): Unit =
    whiteWizard = new WhiteWizard("Lux", 65, 6, 45, 60)

  test("A white wizard has a name, health points, defense, weight and mana"):
    assertEquals(whiteWizard.name, "Lux")
    assertEquals(whiteWizard.healthPoints, 65)
    assertEquals(whiteWizard.defense, 6)
    assertEquals(whiteWizard.weight, 45)
    assertEquals(whiteWizard.mana, 60)

  test("A white wizard with health points is alive"):
    assertEquals(whiteWizard.isAlive, true)

  test("A white wizard with zero health points is not alive"):
    assertEquals(new WhiteWizard("Lux", 0, 6, 45, 60).isAlive, false)

  test("A white wizard starts with an empty weapon slot"):
    assertEquals(whiteWizard.weapon, None)

  test("A white wizard starts with an empty inventory"):
    assertEquals(whiteWizard.inventory, List())

  test("A white wizard can store items in its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("DEMACIA", 30, 10)
    whiteWizard.addItem(potion)
    whiteWizard.addItem(sword)
    assertEquals(whiteWizard.inventory, List(potion, sword))


  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = whiteWizard.inventory
    whiteWizard.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("Two white wizards with the same attributes are equal"):
    val other = new WhiteWizard("Lux", 65, 6, 45, 60)
    assertEquals(whiteWizard, other)
    assertEquals(other, whiteWizard)

  test("A white wizard is not equal to a black wizard with the same attributes"):
    val other: GameUnit = new BlackWizard("Ryze", 65, 6, 45, 60)
    assertNotEquals(other, whiteWizard)

  test("A white wizard is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Sylas", 65, 6, 45, 10)
    assertNotEquals(enemy, whiteWizard)