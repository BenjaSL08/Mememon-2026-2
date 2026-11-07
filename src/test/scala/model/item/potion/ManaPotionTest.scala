package cl.uchile.dcc
package model.item.potion

class ManaPotionTest extends munit.FunSuite:
  var manaPotion: ManaPotion = null

  override def beforeEach(context: BeforeEach): Unit =
    manaPotion = new ManaPotion("Ether")

  test("A mana potion has a name"):
    assertEquals(manaPotion.name, "Ether")

  test("Two mana potions with the same name are equal"):
    val other = new ManaPotion("Ether")
    assertEquals(manaPotion, other)
    assertEquals(other, manaPotion)

  test("Two mana potions with different names are not equal"):
    assertNotEquals(manaPotion, new ManaPotion("Ether Plus"))

  test("A mana potion is not equal to a magic strength potion with the same name"):
    val other: Potion = new MagicStrengthPotion("Ether")
    assertNotEquals(other, manaPotion)