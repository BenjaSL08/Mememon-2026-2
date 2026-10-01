package cl.uchile.dcc
package model.unit

class EnemyTest extends munit.FunSuite:
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    enemy = new Enemy("Goblin", 50, 5, 30, 12)

  test("An enemy has a name, health points, defense, weight and attack"):
    assertEquals(enemy.name, "Goblin")
    assertEquals(enemy.healthPoints, 50)
    assertEquals(enemy.defense, 5)
    assertEquals(enemy.weight, 30)
    assertEquals(enemy.attack, 12)

  test("An enemy with health points is alive"):
    assert(enemy.isAlive)
    assert(new Enemy("Goblin", 1, 5, 30, 12).isAlive)

  test("An enemy with zero health points is not alive"):
    assert(!new Enemy("Goblin", 0, 5, 30, 12).isAlive)

  test("An enemy is equal to itself"):
    assertEquals(enemy, enemy)

  test("Two enemies with the same attributes are equal and have the same hash code"):
    val other = new Enemy("Goblin", 50, 5, 30, 12)
    assertEquals(enemy, other)
    assertEquals(other, enemy)
    assertEquals(enemy.hashCode, other.hashCode)

  test("Two enemies with a different attribute are not equal"):
    assertNotEquals(enemy, new Enemy("Orc", 50, 5, 30, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 40, 5, 30, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 50, 8, 30, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 50, 5, 25, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 50, 5, 30, 20))

  test("An enemy is not equal to null"):
    assert(!enemy.equals(null))