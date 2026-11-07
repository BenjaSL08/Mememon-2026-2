package cl.uchile.dcc
package model.item.weapon

class DaggerTest extends munit.FunSuite:
  var dagger: Dagger = null

  override def beforeEach(context: BeforeEach): Unit =
    dagger = new Dagger("Assassin Blade", 20, 4)

  test("A dagger has a name, attack points and weight"):
    assertEquals(dagger.name, "Assassin Blade")
    assertEquals(dagger.attack, 20)
    assertEquals(dagger.weight, 4)

  test("A dagger starts without an owner"):
    assertEquals(dagger.owner, None)

  test("Two daggers with the same attributes are equal"):
    val other = new Dagger("Assassin Blade", 20, 4)
    assertEquals(dagger, other)
    assertEquals(other, dagger)

  test("Two daggers with a different attribute are not equal"):
    assertNotEquals(dagger, new Dagger("Assassin Blade II", 20, 4))
    assertNotEquals(dagger, new Dagger("Assassin Blade", 25, 4))
    assertNotEquals(dagger, new Dagger("Assassin Blade", 20, 6))

  test("A dagger is not equal to a sword with the same attributes"):
    val other: Weapon = new Sword("Assassin Blade", 20, 4)
    assertNotEquals(other, dagger)