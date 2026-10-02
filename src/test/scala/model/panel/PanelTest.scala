package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.Knight

class PanelTest extends munit.FunSuite:
  var panel: Panel = null

  override def beforeEach(context: BeforeEach): Unit =
    panel = new Panel(2, 3)

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
    val left = new Panel(1, 3)
    val right = new Panel(3, 3)
    val down = new Panel(2, 2)
    val up = new Panel(2, 4)
    panel.addNeighbor(left)
    panel.addNeighbor(right)
    panel.addNeighbor(down)
    panel.addNeighbor(up)
    assertEquals(panel.neighbors, List(left, right, down, up))

  test("A panel in a corner of the map can have only two neighbors"):
    val corner = new Panel(1, 1)
    val right = new Panel(2, 1)
    val up = new Panel(1, 2)
    corner.addNeighbor(right)
    corner.addNeighbor(up)
    assertEquals(corner.neighbors, List(right, up))

  test("Adding a neighbor does not change a list of neighbors obtained before"):
    val neighborsBefore = panel.neighbors
    panel.addNeighbor(new Panel(1, 3))
    assertEquals(neighborsBefore, List())