package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.item.weapon.Wand
import cl.uchile.dcc.model.panel.GridPanel
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.Knight
import cl.uchile.dcc.model.unit.character.WhiteWizard

class AttackTest extends munit.FunSuite:
  var attack: Attack = null
  var knight: Knight = null
  var sword: Sword = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    attack = new Attack()
    knight = new Knight("Garen", 100, 20, 80)
    sword = new Sword("Excalibur", 30, 10)
    enemy = new Enemy("Goblin", 50, 5, 30, 12)
    knight.addItem(sword)
    knight.doAction(new EquipWeapon(knight.weapons), sword)

  test("An attack is an action named Attack"):
    val action: Action = new Attack()
    assertEquals(action.name, "Attack")

  test("A character attacks with the attack points of its weapon minus the defense of the target"):
    knight.doAction(attack, enemy)
    assertEquals(enemy.healthPoints, 50 - (30 - 5))

  test("An enemy attacks with its own attack points minus the defense of the target"):
    val weakKnight = new Knight("Jarvan", 100, 2, 80)
    enemy.doAction(attack, weakKnight)
    assertEquals(weakKnight.healthPoints, 100 - (12 - 2))

  test("An attack weaker than the defense of the target does no damage"):
    enemy.doAction(attack, knight)
    assertEquals(knight.healthPoints, 100)

  test("An attack that defeats the target leaves it with zero health points"):
    val strongEnemy = new Enemy("Baron Nashor", 300, 30, 200, 200)
    strongEnemy.doAction(attack, enemy)
    assertEquals(enemy.healthPoints, 0)
    assertEquals(enemy.isAlive, false)

  test("A character without a weapon cannot attack"):
    val unarmed = new Knight("Jarvan", 100, 20, 80)
    assertEquals(InvalidActionCheck.fails(() => unarmed.doAction(attack, enemy)), true)
    assertEquals(enemy.healthPoints, 50)

  test("Wizards can attack with their weapons"):
    val blackWizard = new BlackWizard("Ryze", 60, 5, 40, 50)
    val wand = new Wand("Elder Wand", 10, 4, 30)
    blackWizard.addItem(wand)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), wand)
    blackWizard.doAction(attack, enemy)
    assertEquals(enemy.healthPoints, 50 - (10 - 5))
    val whiteWizard = new WhiteWizard("Lux", 65, 6, 45, 60)
    val staff = new Staff("Healing Staff", 15, 9, 40)
    whiteWizard.addItem(staff)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), staff)
    whiteWizard.doAction(attack, enemy)
    assertEquals(enemy.healthPoints, 45 - (15 - 5))

  test("A panel cannot be attacked"):
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(attack, new GridPanel(1, 1))), true)

  test("An item cannot be attacked"):
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(attack, new HealingPotion("Potion"))), true)

  test("A defeated unit cannot attack"):
    enemy.receiveDamage(50)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(attack, knight)), true)
    assertEquals(knight.healthPoints, 100)

  test("A defeated unit cannot be attacked"):
    enemy.receiveDamage(50)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(attack, enemy)), true)