package cl.uchile.dcc
package model.action

class MoveTest extends munit.FunSuite:

  test("A move is an action named Move"):
    val action: Action = new Move()
    assertEquals(action.name, "Move")