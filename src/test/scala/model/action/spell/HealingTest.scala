package cl.uchile.dcc
package model.action.spell

class HealingTest extends munit.FunSuite:

  test("Healing is a white magic spell named Healing"):
    val spell: WhiteMagicSpell = new Healing()
    assertEquals(spell.name, "Healing")