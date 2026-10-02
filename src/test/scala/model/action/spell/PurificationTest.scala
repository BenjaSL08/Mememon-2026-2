package cl.uchile.dcc
package model.action.spell

class PurificationTest extends munit.FunSuite:

  test("Purification is a white magic spell named Purification"):
    val spell: WhiteMagicSpell = new Purification()
    assertEquals(spell.name, "Purification")