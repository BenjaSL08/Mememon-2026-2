package cl.uchile.dcc
package model.item.potion

class FortitudePotionTest extends munit.FunSuite:
  var fortitudePotion: FortitudePotion = null

  override def beforeEach(context: BeforeEach): Unit =
    fortitudePotion = new FortitudePotion("Giant Draught")

  test("A fortitude potion has a name"):
    assertEquals(fortitudePotion.name, "Giant Draught")

  test("Two fortitude potions with the same name are equal and have the same hash code"):
    val other = new FortitudePotion("Giant Draught")
    assertEquals(fortitudePotion, other)
    assertEquals(other, fortitudePotion)
    assertEquals(fortitudePotion.hashCode, other.hashCode)

  test("Two fortitude potions with different names are not equal"):
    assertNotEquals(fortitudePotion, new FortitudePotion("Giant Draught Plus"))

  test("A fortitude potion is not equal to a healing potion with the same name"):
    val other: Potion = new HealingPotion("Giant Draught")
    assertNotEquals(other, fortitudePotion)

  test("A fortitude potion is not equal to null"):
    assert(!fortitudePotion.equals(null))