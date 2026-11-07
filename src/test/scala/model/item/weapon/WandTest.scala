package cl.uchile.dcc
package model.item.weapon

class WandTest extends munit.FunSuite:
  var wand: Wand = null

  override def beforeEach(context: BeforeEach): Unit =
    wand = new Wand("Mage Masher", 8, 3, 25)

  test("A wand has a name, attack points, weight and magic attack points"):
    assertEquals(wand.name, "Mage Masher")
    assertEquals(wand.attack, 8)
    assertEquals(wand.weight, 3)
    assertEquals(wand.magicAttack, 25)

  test("A wand starts without an owner"):
    assertEquals(wand.owner, None)

  test("Two wands with the same attributes are equal"):
    val other = new Wand("Mage Masher", 8, 3, 25)
    assertEquals(wand, other)
    assertEquals(other, wand)

  test("Two wands with a different attribute are not equal"):
    assertNotEquals(wand, new Wand("Mage Masher II", 8, 3, 25))
    assertNotEquals(wand, new Wand("Mage Masher", 13, 3, 25))
    assertNotEquals(wand, new Wand("Mage Masher", 8, 5, 25))
    assertNotEquals(wand, new Wand("Mage Masher", 8, 3, 30))

  test("A wand is not equal to a staff with the same attributes"):
    val other: Weapon = new Staff("Mage Masher", 8, 3, 25)
    assertNotEquals(other, wand)