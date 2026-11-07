package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.panel.GridPanel
import cl.uchile.dcc.model.panel.NoPanel
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.Knight

class MoveTest extends munit.FunSuite:
  var move: Move = null
  var origin: GridPanel = null
  var right: GridPanel = null
  var far: GridPanel = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    move = new Move()
    origin = new GridPanel(1, 1)
    right = new GridPanel(2, 1)
    far = new GridPanel(3, 1)
    origin.addNeighbor(right)
    right.addNeighbor(origin)
    right.addNeighbor(far)
    far.addNeighbor(right)
    enemy = new Enemy("Goblin", 50, 5, 30, 12)
    enemy.placeOn(origin)

  test("A move is an action named Move"):
    val action: Action = new Move()
    assertEquals(action.name, "Move")

  test("A unit can move to an adjacent panel"):
    enemy.doAction(move, right)
    assertEquals(enemy.panel, right)
    assertEquals(origin.units, List())
    assertEquals(right.units, List(enemy))

  test("A character can move to an adjacent panel"):
    val knight = new Knight("Garen", 100, 20, 80)
    knight.placeOn(origin)
    knight.doAction(move, right)
    assertEquals(knight.panel, right)

  test("A unit cannot move to a panel that is not adjacent"):
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(move, far)), true)
    assertEquals(enemy.panel, origin)
    assertEquals(far.units, List())

  test("A unit that is not on the map cannot move"):
    val knight = new Knight("Garen", 100, 20, 80)
    assertEquals(knight.panel, NoPanel)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(move, right)), true)
    assertEquals(knight.panel, NoPanel)

  test("A unit cannot move to something that is not a panel"):
    val knight = new Knight("Garen", 100, 20, 80)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(move, knight)), true)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(move, new HealingPotion("Potion"))), true)
    assertEquals(enemy.panel, origin)

  test("A defeated unit cannot move"):
    enemy.receiveDamage(50)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(move, right)), true)
    assertEquals(enemy.panel, origin)