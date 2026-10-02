package cl.uchile.dcc
package model.item.potion

class HealingPotionTest extends munit.FunSuite:
  var healingPotion: HealingPotion = null

  override def beforeEach(context: BeforeEach): Unit =
    healingPotion = new HealingPotion("Hi-Potion")

  test("A healing potion has a name"):
    assertEquals(healingPotion.name, "Hi-Potion")

  test("Two healing potions with the same name are equal and have the same hash code"):
    val other = new HealingPotion("Hi-Potion")
    assertEquals(healingPotion, other)
    assertEquals(other, healingPotion)
    assertEquals(healingPotion.hashCode, other.hashCode)

  test("Two healing potions with different names are not equal"):
    assertNotEquals(healingPotion, new HealingPotion("Hi-Potion Plus"))

  test("A healing potion is not equal to a fortitude potion with the same name"):
    val other: Potion = new FortitudePotion("Hi-Potion")
    assertNotEquals(other, healingPotion)

  test("A healing potion is not equal to null"):
    assert(!healingPotion.equals(null))