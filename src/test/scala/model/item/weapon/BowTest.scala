package cl.uchile.dcc
package model.item.weapon

class BowTest extends munit.FunSuite:
  var bow: Bow = null

  override def beforeEach(context: BeforeEach): Unit =
    bow = new Bow("Yoichi Bow", 25, 6)

  test("A bow has a name, attack points and weight"):
    assertEquals(bow.name, "Yoichi Bow")
    assertEquals(bow.attack, 25)
    assertEquals(bow.weight, 6)

  test("A bow starts without an owner"):
    assertEquals(bow.owner, None)

  test("Two bows with the same attributes are equal"):
    val other = new Bow("Yoichi Bow", 25, 6)
    assertEquals(bow, other)
    assertEquals(other, bow)

  test("Two bows with a different attribute are not equal"):
    assertNotEquals(bow, new Bow("Yoichi Bow II", 25, 6))
    assertNotEquals(bow, new Bow("Yoichi Bow", 30, 6))
    assertNotEquals(bow, new Bow("Yoichi Bow", 25, 8))

  test("A bow is not equal to a sword with the same attributes"):
    val other: Weapon = new Sword("Yoichi Bow", 25, 6)
    assertNotEquals(other, bow)