package cl.uchile.dcc
package model.action.spell

class ThunderTest extends munit.FunSuite:

  test("Thunder is a black magic spell named Thunder"):
    val spell: BlackMagicSpell = new Thunder()
    assertEquals(spell.name, "Thunder")