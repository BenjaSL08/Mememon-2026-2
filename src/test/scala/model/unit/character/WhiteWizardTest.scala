package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.{Enemy, GameUnit}

class WhiteWizardTest extends munit.FunSuite:
  var whiteWizard: WhiteWizard = null

  override def beforeEach(context: BeforeEach): Unit =
    whiteWizard = new WhiteWizard("Rosa", 65, 6, 45, 60)

  test("A white wizard has a name, health points, defense, weight and mana"):
    assertEquals(whiteWizard.name, "Rosa")
    assertEquals(whiteWizard.healthPoints, 65)
    assertEquals(whiteWizard.defense, 6)
    assertEquals(whiteWizard.weight, 45)
    assertEquals(whiteWizard.mana, 60)

  test("A white wizard with health points is alive"):
    assertEquals(whiteWizard.isAlive, true)

  test("A white wizard with zero health points is not alive"):
    assertEquals(new WhiteWizard("Rosa", 0, 6, 45, 60).isAlive, false)

  test("A white wizard starts with an empty weapon slot"):
    assertEquals(whiteWizard.weapon, None)

  test("A white wizard starts with an empty inventory"):
    assertEquals(whiteWizard.inventory, List())

  test("A white wizard can store items in its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    whiteWizard.addItem(potion)
    whiteWizard.addItem(sword)
    assertEquals(whiteWizard.inventory, List(potion, sword))

  test("A white wizard can remove an item from its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    whiteWizard.addItem(potion)
    whiteWizard.addItem(sword)
    whiteWizard.removeItem(potion)
    assertEquals(whiteWizard.inventory, List(sword))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = whiteWizard.inventory
    whiteWizard.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("Two white wizards with the same attributes are equal"):
    val other = new WhiteWizard("Rosa", 65, 6, 45, 60)
    assertEquals(whiteWizard, other)
    assertEquals(other, whiteWizard)

  test("Two white wizards with a different attribute are not equal"):
    assertNotEquals(whiteWizard, new WhiteWizard("Rosa II", 65, 6, 45, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Rosa", 55, 6, 45, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Rosa", 65, 9, 45, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Rosa", 65, 6, 50, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Rosa", 65, 6, 45, 70))

  test("A white wizard is not equal to a black wizard with the same attributes"):
    val other: GameUnit = new BlackWizard("Rosa", 65, 6, 45, 60)
    assertNotEquals(other, whiteWizard)

  test("A white wizard is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Rosa", 65, 6, 45, 10)
    assertNotEquals(enemy, whiteWizard)