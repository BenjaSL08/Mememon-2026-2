package cl.uchile.dcc
package model.action

class AttackTest extends munit.FunSuite:

  test("An attack is an action named Attack"):
    val action: Action = new Attack()
    assertEquals(action.name, "Attack")