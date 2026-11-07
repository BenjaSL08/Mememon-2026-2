package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.action.InvalidActionCheck
import cl.uchile.dcc.model.unit.character.Knight

class SwordTest extends munit.FunSuite:
  var sword: Sword = null

  override def beforeEach(context: BeforeEach): Unit =
    sword = new Sword("Excalibur", 30, 10)

  test("A sword has a name, attack points and weight"):
    assertEquals(sword.name, "Excalibur")
    assertEquals(sword.attack, 30)
    assertEquals(sword.weight, 10)

  test("A sword starts without an owner"):
    assertEquals(sword.owner, None)

  test("Two swords with the same attributes are equal"):
    val other = new Sword("Excalibur", 30, 10)
    assertEquals(sword, other)
    assertEquals(other, sword)

  test("Two swords with a different attribute are not equal"):
    assertNotEquals(sword, new Sword("Excalibur II", 30, 10))
    assertNotEquals(sword, new Sword("Excalibur", 35, 10))
    assertNotEquals(sword, new Sword("Excalibur", 30, 12))

  test("A sword is not equal to a dagger with the same attributes"):
    val other: Weapon = new Dagger("Excalibur", 30, 10)
    assertNotEquals(other, sword)

  test("A sword attacks with its attack points and cannot be used to cast spells"):
    assertEquals(sword.attackDamage, 30)
    assertEquals(InvalidActionCheck.fails(() => sword.magicDamage), true)

  test("A sword can get an owner and lose it"):
    val knight = new Knight("Garen", 100, 20, 80)
    sword.assignOwner(knight)
    assertEquals(sword.owner, Some(knight))
    sword.release()
    assertEquals(sword.owner, None)