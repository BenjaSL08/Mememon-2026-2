package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.GameUnit

class BlackWizardTest extends munit.FunSuite:
  var blackWizard: BlackWizard = null

  override def beforeEach(context: BeforeEach): Unit =
    blackWizard = new BlackWizard("Vivi", 60, 5, 40, 50)

  test("A black wizard has a name, health points, defense, weight and mana"):
    assertEquals(blackWizard.name, "Vivi")
    assertEquals(blackWizard.healthPoints, 60)
    assertEquals(blackWizard.defense, 5)
    assertEquals(blackWizard.weight, 40)
    assertEquals(blackWizard.mana, 50)

  test("A black wizard with health points is alive"):
    assertEquals(blackWizard.isAlive, true)

  test("A black wizard with zero health points is not alive"):
    assertEquals(new BlackWizard("Vivi", 0, 5, 40, 50).isAlive, false)

  test("A black wizard starts with an empty weapon slot"):
    assertEquals(blackWizard.weapon, None)

  test("A black wizard starts with an empty inventory"):
    assertEquals(blackWizard.inventory, List())

  test("A black wizard can store items in its inventory"):
    val potion = new HealingPotion("Potion")
    val sword = new Sword("Excalibur", 30, 10)
    blackWizard.addItem(potion)
    blackWizard.addItem(sword)
    assertEquals(blackWizard.inventory, List(potion, sword))
  

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = blackWizard.inventory
    blackWizard.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("Two black wizards with the same attributes are equal"):
    val other = new BlackWizard("Vivi", 60, 5, 40, 50)
    assertEquals(blackWizard, other)
    assertEquals(other, blackWizard)

  test("Two black wizards with a different attribute are not equal"):
    assertNotEquals(blackWizard, new BlackWizard("Vivi II", 60, 5, 40, 50))
    assertNotEquals(blackWizard, new BlackWizard("Vivi", 50, 5, 40, 50))
    assertNotEquals(blackWizard, new BlackWizard("Vivi", 60, 8, 40, 50))
    assertNotEquals(blackWizard, new BlackWizard("Vivi", 60, 5, 45, 50))
    assertNotEquals(blackWizard, new BlackWizard("Vivi", 60, 5, 40, 60))

  test("A black wizard is not equal to a white wizard with the same attributes"):
    val other: GameUnit = new WhiteWizard("Vivi", 60, 5, 40, 50)
    assertNotEquals(other, blackWizard)

  test("A black wizard is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Vivi", 60, 5, 40, 10)
    assertNotEquals(enemy, blackWizard)