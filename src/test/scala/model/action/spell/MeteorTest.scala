package cl.uchile.dcc
package model.action.spell

class MeteorTest extends munit.FunSuite:

  test("Meteor is a black magic spell named Meteor"):
    val spell: BlackMagicSpell = new Meteor()
    assertEquals(spell.name, "Meteor")