package cl.uchile.dcc
package controller

import cl.uchile.dcc.model.action.Attack
import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.Knight

class GameControllerTest extends munit.FunSuite:
  var controller: GameController = null
  var knight: Knight = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    controller = new GameController()
    knight = new Knight("Garen", 100, 20, 80)
    enemy = new Enemy("Goblin", 50, 5, 30, 30)
    controller.addUnit(knight)
    controller.addUnit(enemy)

  test("A game starts without a unit playing"):
    assertEquals(controller.currentUnit, None)

  test("Units can be added to the game"):
    assertEquals(controller.units, List(knight, enemy))

  test("Without units, nobody gets the turn"):
    val empty = new GameController()
    assertEquals(empty.nextTurn(), None)
    assertEquals(empty.currentUnit, None)

  test("The next turn goes to the unit that completes its action bar first"):
    assertEquals(controller.nextTurn(), Some(enemy))
    assertEquals(controller.currentUnit, Some(enemy))

  test("The controller knows the actions of a unit"):
    assertEquals(controller.actionsOf(enemy).length, 2)
    assertEquals(controller.actionsOf(knight).length, 4)

  test("A unit cannot act when nobody has the turn"):
    assertEquals(controller.executeAction(new Attack(), knight), false)
    assertEquals(controller.lastError, Some("No unit has the turn"))
    assertEquals(knight.healthPoints, 100)

  test("A successful action ends the turn"):
    controller.nextTurn()
    assertEquals(controller.executeAction(new Attack(), knight), true)
    assertEquals(controller.lastError, None)
    assertEquals(knight.healthPoints, 90)
    assertEquals(controller.currentUnit, None)

  test("A failed action does not change the turn"):
    controller.nextTurn()
    val sword = new Sword("Excalibur", 30, 10)
    assertEquals(controller.executeAction(new EquipWeapon(List(sword)), sword), false)
    assertEquals(controller.lastError, Some("Goblin cannot do Equip weapon"))
    assertEquals(controller.currentUnit, Some(enemy))

  test("After acting, a unit waits again for its turn"):
    controller.nextTurn()
    controller.executeAction(new Attack(), knight)
    controller.nextTurn()
    controller.executeAction(new Attack(), knight)
    assertEquals(controller.nextTurn(), Some(knight))

  test("A defeated unit leaves the game"):
    val weakKnight = new Knight("Jarvan", 5, 0, 80)
    controller.addUnit(weakKnight)
    controller.nextTurn()
    controller.executeAction(new Attack(), weakKnight)
    assertEquals(controller.units, List(knight, enemy))

  test("Resetting the game empties the action bars and nobody has the turn"):
    controller.nextTurn()
    controller.reset()
    assertEquals(controller.currentUnit, None)
    assertEquals(controller.nextTurn(), Some(enemy))