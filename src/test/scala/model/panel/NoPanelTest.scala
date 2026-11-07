package cl.uchile.dcc
package model.panel

import cl.uchile.dcc.model.unit.Enemy

class NoPanelTest extends munit.FunSuite:

  test("A unit starts on no panel"):
    val enemy = new Enemy("Goblin", 50, 5, 30, 12)
    assertEquals(enemy.panel, NoPanel)

  test("No panel has default coordinates"):
    assertEquals(NoPanel.x, 0)
    assertEquals(NoPanel.y, 0)

  test("No panel never holds units"):
    val enemy = new Enemy("Goblin", 50, 5, 30, 12)
    NoPanel.addUnit(enemy)
    assertEquals(NoPanel.units, List())
    NoPanel.removeUnit(enemy)
    assertEquals(NoPanel.units, List())

  test("No panel never has neighbors"):
    val panel = new GridPanel(1, 1)
    NoPanel.addNeighbor(panel)
    assertEquals(NoPanel.neighbors, List())
    assertEquals(NoPanel.isNeighbor(panel), false)