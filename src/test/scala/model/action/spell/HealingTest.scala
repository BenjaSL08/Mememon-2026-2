package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.action.InvalidActionCheck
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.panel.GridPanel
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.Knight
import cl.uchile.dcc.model.unit.character.WhiteWizard

class HealingTest extends munit.FunSuite:
  var healing: Healing = null
  var wizard: WhiteWizard = null
  var knight: Knight = null

  override def beforeEach(context: BeforeEach): Unit =
    healing = new Healing()
    wizard = new WhiteWizard("Lux", 65, 6, 45, 60)
    val staff = new Staff("Healing Staff", 15, 9, 40)
    wizard.addItem(staff)
    wizard.doAction(new EquipWeapon(wizard.weapons), staff)
    knight = new Knight("Garen", 100, 20, 80)

  test("Healing is a white magic spell named Healing"):
    val spell: WhiteMagicSpell = new Healing()
    assertEquals(spell.name, "Healing")

  test("Healing restores 20% of the maximum health points plus a quarter of the magic damage"):
    knight.receiveDamage(60)
    wizard.doAction(healing, knight)
    assertEquals(knight.healthPoints, 40 + 20 + 10)
    assertEquals(wizard.mana, 45)

  test("Healing never heals above the maximum health points"):
    knight.receiveDamage(5)
    wizard.doAction(healing, knight)
    assertEquals(knight.healthPoints, 100)

  test("A white wizard can heal itself"):
    wizard.receiveDamage(30)
    wizard.doAction(healing, wizard)
    assertEquals(wizard.healthPoints, 35 + 13 + 10)

  test("Healing cannot target an enemy"):
    val enemy = new Enemy("Goblin", 50, 5, 30, 12)
    enemy.receiveDamage(20)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(healing, enemy)), true)
    assertEquals(enemy.healthPoints, 30)
    assertEquals(wizard.mana, 60)

  test("Healing cannot target a panel"):
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(healing, new GridPanel(1, 1))), true)

  test("A white wizard without enough mana cannot cast Healing"):
    for i <- 1 to 4 do wizard.doAction(healing, knight)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(healing, knight)), true)
    assertEquals(wizard.mana, 0)

  test("Only white wizards can cast Healing"):
    val blackWizard = new BlackWizard("Ryze", 60, 5, 40, 50)
    knight.receiveDamage(60)
    assertEquals(InvalidActionCheck.fails(() => blackWizard.doAction(healing, knight)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(healing, knight)), true)
    assertEquals(knight.healthPoints, 40)

  test("A defeated character cannot be healed"):
    knight.receiveDamage(100)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(healing, knight)), true)
    assertEquals(knight.healthPoints, 0)
    assertEquals(wizard.mana, 60)