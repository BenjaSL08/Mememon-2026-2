package cl.uchile.dcc
package model.action.spell

import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.action.InvalidActionCheck
import cl.uchile.dcc.model.item.weapon.Dagger
import cl.uchile.dcc.model.item.weapon.Wand
import cl.uchile.dcc.model.panel.GridPanel
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.Knight
import cl.uchile.dcc.model.unit.character.WhiteWizard

class ThunderTest extends munit.FunSuite:
  var thunder: Thunder = null
  var wizard: BlackWizard = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    thunder = new Thunder()
    wizard = new BlackWizard("Ryze", 60, 5, 40, 50)
    val wand = new Wand("Elder Wand", 10, 4, 30)
    wizard.addItem(wand)
    wizard.doAction(new EquipWeapon(wizard.weapons), wand)
    enemy = new Enemy("Goblin", 50, 5, 30, 12)

  test("Thunder is a black magic spell named Thunder"):
    val spell: BlackMagicSpell = new Thunder()
    assertEquals(spell.name, "Thunder")

  test("Thunder lowers the health points of the target by the magic damage and costs 20 mana"):
    wizard.doAction(thunder, enemy)
    assertEquals(enemy.healthPoints, 20)
    assertEquals(wizard.mana, 30)

  test("Thunder ignores the defense of the target"):
    val knight = new Knight("Garen", 100, 40, 80)
    wizard.doAction(thunder, knight)
    assertEquals(knight.healthPoints, 70)

  test("A black wizard cannot cast Thunder with a common weapon"):
    val other = new BlackWizard("Zed", 60, 5, 40, 50)
    val dagger = new Dagger("Kunai", 20, 5)
    other.addItem(dagger)
    other.doAction(new EquipWeapon(other.weapons), dagger)
    assertEquals(InvalidActionCheck.fails(() => other.doAction(thunder, enemy)), true)
    assertEquals(enemy.healthPoints, 50)
    assertEquals(other.mana, 50)

  test("A black wizard cannot cast Thunder without a weapon"):
    val other = new BlackWizard("Zed", 60, 5, 40, 50)
    assertEquals(InvalidActionCheck.fails(() => other.doAction(thunder, enemy)), true)
    assertEquals(other.mana, 50)

  test("A defeated unit cannot be hit by Thunder"):
    enemy.receiveDamage(50)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(thunder, enemy)), true)
    assertEquals(wizard.mana, 50)

  test("A black wizard without enough mana cannot cast Thunder"):
    wizard.doAction(thunder, enemy)
    wizard.doAction(thunder, enemy)
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(thunder, enemy)), true)
    assertEquals(enemy.healthPoints, 0)
    assertEquals(wizard.mana, 10)

  test("Thunder cannot target a panel"):
    assertEquals(InvalidActionCheck.fails(() => wizard.doAction(thunder, new GridPanel(1, 1))), true)
    assertEquals(wizard.mana, 50)

  test("Only black wizards can cast Thunder"):
    val whiteWizard = new WhiteWizard("Lux", 65, 6, 45, 60)
    val knight = new Knight("Garen", 100, 20, 80)
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.doAction(thunder, enemy)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(thunder, enemy)), true)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(thunder, knight)), true)
    assertEquals(enemy.healthPoints, 50)