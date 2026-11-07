package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.action.InvalidActionCheck
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.Knight
import cl.uchile.dcc.model.unit.character.WhiteWizard

class PurificationTest extends munit.FunSuite:
  var purification: Purification = null
  var wizard: WhiteWizard = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    purification = new Purification()
    wizard = new WhiteWizard("Lux", 65, 6, 45, 60)
    val staff = new Staff("Healing Staff", 15, 9, 40)
    wizard.addItem(staff)
    wizard.doAction(new EquipWeapon(wizard.weapons), staff)
    enemy = new Enemy("Goblin", 60, 5, 30, 12)

  test("Purification is a white magic spell named Purification"):
    val spell: WhiteMagicSpell = new Purification()
    assertEquals(spell.name, "Purification")

  test("Purification removes 15% of the current health points plus half the magic damage"):
    wizard.doAction(purification, enemy)
    assertEquals(enemy.healthPoints, 60 - (9 + 20))
    assertEquals(wizard.mana, 35)

  test("Purification uses the current health points, not the maximum"):
    enemy.receiveDamage(20)
    wizard.doAction(purification, enemy)
    assertEquals(enemy.healthPoints, 40 - (6 + 20))

  test("Purification cannot target a character"):
    val knight = new Knight("Garen", 100, 20, 80)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(purification, knight)), true)
    assertEquals(knight.healthPoints, 100)
    assertEquals(wizard.mana, 60)

  test("A white wizard without enough mana cannot cast Purification"):
    wizard.doAction(purification, enemy)
    wizard.doAction(purification, enemy)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(purification, enemy)), true)
    assertEquals(wizard.mana, 10)

  test("Only white wizards can cast Purification"):
    val blackWizard = new BlackWizard("Ryze", 60, 5, 40, 50)
    assertEquals(InvalidActionCheck.fails(() => blackWizard.doAction(purification, enemy)), true)
    assertEquals(enemy.healthPoints, 60)

  test("A defeated enemy cannot be purified"):
    enemy.receiveDamage(60)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(purification, enemy)), true)
    assertEquals(wizard.mana, 60)