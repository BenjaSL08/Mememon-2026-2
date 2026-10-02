package cl.uchile.dcc
package model.item.potion

class MagicStrengthPotionTest extends munit.FunSuite:
  var magicStrengthPotion: MagicStrengthPotion = null

  override def beforeEach(context: BeforeEach): Unit =
    magicStrengthPotion = new MagicStrengthPotion("Arcane Tonic")

  test("A magic strength potion has a name"):
    assertEquals(magicStrengthPotion.name, "Arcane Tonic")

  test("Two magic strength potions with the same name are equal and have the same hash code"):
    val other = new MagicStrengthPotion("Arcane Tonic")
    assertEquals(magicStrengthPotion, other)
    assertEquals(other, magicStrengthPotion)
    assertEquals(magicStrengthPotion.hashCode, other.hashCode)

  test("Two magic strength potions with different names are not equal"):
    assertNotEquals(magicStrengthPotion, new MagicStrengthPotion("Arcane Tonic Plus"))

  test("A magic strength potion is not equal to a mana potion with the same name"):
    val other: Potion = new ManaPotion("Arcane Tonic")
    assertNotEquals(other, magicStrengthPotion)

  test("A magic strength potion is not equal to null"):
    assert(!magicStrengthPotion.equals(null))