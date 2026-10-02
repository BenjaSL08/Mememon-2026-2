package cl.uchile.dcc
package model.item.weapon

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
