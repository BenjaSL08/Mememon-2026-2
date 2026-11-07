package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.Knight

class PanelTest extends munit.FunSuite:
  var panel: Panel = null

  override def beforeEach(context: BeforeEach): Unit =
    panel = new GridPanel(2, 3)

  test("A panel has coordinates"):
    assertEquals(panel.x, 2)
    assertEquals(panel.y, 3)

  test("A panel starts without units"):
    assertEquals(panel.units, List())

  test("A panel can hold characters and enemies"):
    val knight = new Knight("Cristiano Ronaldo", 100, 20, 80)
    val enemy = new Enemy("Messi", 50, 5, 30, 12)
    panel.addUnit(knight)
    panel.addUnit(enemy)
    assertEquals(panel.units, List(knight, enemy))

  test("Adding a unit does not change a list of units obtained before"):
    val unitsBefore = panel.units
    panel.addUnit(new Knight("Cristiano Ronaldo", 100, 20, 80))
    assertEquals(unitsBefore, List())

  test("A panel starts without neighbors"):
    assertEquals(panel.neighbors, List())

  test("A panel in the middle of the map can have four neighbors"):
    val left = new GridPanel(1, 3)
    val right = new GridPanel(3, 3)
    val down = new GridPanel(2, 2)
    val up = new GridPanel(2, 4)
    panel.addNeighbor(left)
    panel.addNeighbor(right)
    panel.addNeighbor(down)
    panel.addNeighbor(up)
    assertEquals(panel.neighbors, List(left, right, down, up))

  test("A panel in a corner of the map can have only two neighbors"):
    val corner = new GridPanel(1, 1)
    val right = new GridPanel(2, 1)
    val up = new GridPanel(1, 2)
    corner.addNeighbor(right)
    corner.addNeighbor(up)
    assertEquals(corner.neighbors, List(right, up))

  test("Adding a neighbor does not change a list of neighbors obtained before"):
    val neighborsBefore = panel.neighbors
    panel.addNeighbor(new GridPanel(1, 3))
    assertEquals(neighborsBefore, List())

  test("A unit can be removed from a panel"):
    val knight = new Knight("Cristiano Ronaldo", 100, 20, 80)
    val enemy = new Enemy("Messi", 50, 5, 30, 12)
    panel.addUnit(knight)
    panel.addUnit(enemy)
    panel.removeUnit(knight)
    assertEquals(panel.units, List(enemy))

  test("Removing a unit that is not on the panel changes nothing"):
    val enemy = new Enemy("Messi", 50, 5, 30, 12)
    panel.addUnit(enemy)
    panel.removeUnit(new Knight("Cristiano Ronaldo", 100, 20, 80))
    assertEquals(panel.units, List(enemy))

  test("A panel knows which panels are adjacent to it"):
    val left = new GridPanel(1, 3)
    panel.addNeighbor(left)
    assertEquals(panel.isNeighbor(left), true)
    assertEquals(panel.isNeighbor(new GridPanel(5, 5)), false)

  test("Placing a unit on a panel updates both the unit and the panel"):
    val enemy = new Enemy("Messi", 50, 5, 30, 12)
    enemy.placeOn(panel)
    assertEquals(enemy.panel, panel)
    assertEquals(panel.units, List(enemy))

  test("A unit placed on another panel leaves the previous one"):
    val enemy = new Enemy("Messi", 50, 5, 30, 12)
    val other = new GridPanel(1, 3)
    enemy.placeOn(panel)
    enemy.placeOn(other)
    assertEquals(panel.units, List())
    assertEquals(other.units, List(enemy))