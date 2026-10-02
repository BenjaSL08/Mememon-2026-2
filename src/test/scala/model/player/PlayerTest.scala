package cl.uchile.dcc
package model.player

import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.{BlackWizard, Knight}

class PlayerTest extends munit.FunSuite:
  var player: Player = null
  var knight: Knight = null
  var wizard: BlackWizard = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    player = new Player()
    knight = new Knight("Arthur", 100, 20, 80)
    wizard = new BlackWizard("Vivi", 60, 5, 40, 50)
    enemy = new Enemy("Goblin", 50, 5, 30, 12)

  test("A player starts without units"):
    assertEquals(player.units, List())

  test("A player can have characters and enemies in the same list of units"):
    player.addUnit(knight)
    player.addUnit(wizard)
    player.addUnit(enemy)
    assertEquals(player.units, List(knight, wizard, enemy))

  test("A player can remove a unit"):
    player.addUnit(knight)
    player.addUnit(wizard)
    player.addUnit(enemy)
    player.removeUnit(wizard)
    assertEquals(player.units, List(knight, enemy))

  test("Removing a unit the player does not have changes nothing"):
    player.addUnit(knight)
    player.removeUnit(enemy)
    assertEquals(player.units, List(knight))

  test("Adding a unit does not change a list of units obtained before"):
    val unitsBefore = player.units
    player.addUnit(knight)
    assertEquals(unitsBefore, List())

  test("A player without units is defeated"):
    assertEquals(player.isDefeated, true)

  test("A player with all its units alive is not defeated"):
    player.addUnit(knight)
    player.addUnit(enemy)
    assertEquals(player.isDefeated, false)

  test("A player with at least one unit alive is not defeated"):
    player.addUnit(new Knight("Arthur", 0, 20, 80))
    player.addUnit(enemy)
    assertEquals(player.isDefeated, false)

  test("A player whose units are all dead is defeated"):
    player.addUnit(new Knight("Arthur", 0, 20, 80))
    player.addUnit(new Enemy("Goblin", 0, 5, 30, 12))
    assertEquals(player.isDefeated, true)