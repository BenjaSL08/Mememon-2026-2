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

class MeteorTest extends munit.FunSuite:
  var meteor: Meteor = null
  var wizard: BlackWizard = null
  var panel: GridPanel = null
  var enemy: Enemy = null
  var knight: Knight = null

  override def beforeEach(context: BeforeEach): Unit =
    meteor = new Meteor()
    wizard = new BlackWizard("Ryze", 60, 5, 40, 60)
    val staff = new Staff("Void Staff", 15, 9, 40)
    wizard.addItem(staff)
    wizard.doAction(new EquipWeapon(wizard.weapons), staff)
    panel = new GridPanel(2, 2)
    enemy = new Enemy("Goblin", 50, 5, 30, 12)
    knight = new Knight("Garen", 100, 20, 80)
    enemy.placeOn(panel)
    knight.placeOn(panel)

  test("Meteor is a black magic spell named Meteor"):
    val spell: BlackMagicSpell = new Meteor()
    assertEquals(spell.name, "Meteor")

  test("Meteor damages every unit on the target panel and costs 50 mana"):
    wizard.doAction(meteor, panel)
    assertEquals(enemy.healthPoints, 10)
    assertEquals(knight.healthPoints, 60)
    assertEquals(wizard.mana, 10)

  test("Meteor on an empty panel only spends mana"):
    wizard.doAction(meteor, new GridPanel(5, 5))
    assertEquals(wizard.mana, 10)

  test("A black wizard without enough mana cannot cast Meteor"):
    wizard.doAction(meteor, panel)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(meteor, panel)), true)
    assertEquals(enemy.healthPoints, 10)

  test("Meteor cannot target a unit"):
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(meteor, enemy)), true)
    assertEquals(wizard.mana, 60)

  test("Only black wizards can cast Meteor"):
    val whiteWizard = new WhiteWizard("Lux", 65, 6, 45, 60)
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.doAction(meteor, panel)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(meteor, panel)), true)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(meteor, panel)), true)