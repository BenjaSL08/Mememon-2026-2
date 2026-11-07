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
    assertEquals(enemy.isAlive, true)
    assertEquals(new Enemy("Goblin", 1, 5, 30, 12).isAlive, true)

  test("An enemy with zero health points is not alive"):
    assertEquals(new Enemy("Goblin", 0, 5, 30, 12).isAlive, false)

  test("An enemy is equal to itself"):
    assertEquals(enemy, enemy)

  test("Two enemies with the same attributes are equal"):
    val other = new Enemy("Goblin", 50, 5, 30, 12)
    assertEquals(enemy, other)
    assertEquals(other, enemy)

  test("Two enemies with a different attribute are not equal"):
    assertNotEquals(enemy, new Enemy("Orc", 50, 5, 30, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 40, 5, 30, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 50, 8, 30, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 50, 5, 25, 12))
    assertNotEquals(enemy, new Enemy("Goblin", 50, 5, 30, 20))

  test("An enemy starts with all its health points"):
    assertEquals(enemy.maxHealthPoints, 50)
    assertEquals(enemy.healthPoints, 50)

  test("An enemy attacks with its own attack points"):
    assertEquals(enemy.attackDamage, 12)

  test("An enemy can only attack and move"):
    assertEquals(enemy.actions.length, 2)
    assertEquals(enemy.actions.head.name, "Attack")
    assertEquals(enemy.findAction("Move").get.name, "Move")

  test("An enemy does not find an action it does not have"):
    assertEquals(enemy.findAction("Thunder"), None)

  test("Damage lowers the health points of an enemy"):
    enemy.receiveDamage(20)
    assertEquals(enemy.healthPoints, 30)

  test("The health points of an enemy never go below zero"):
    enemy.receiveDamage(80)
    assertEquals(enemy.healthPoints, 0)
    assertEquals(enemy.isAlive, false)

  test("A negative damage does not heal an enemy"):
    enemy.receiveDamage(20)
    enemy.receiveDamage(-10)
    assertEquals(enemy.healthPoints, 30)

  test("Healing restores health points up to the maximum"):
    enemy.receiveDamage(20)
    enemy.heal(5)
    assertEquals(enemy.healthPoints, 35)
    enemy.heal(100)
    assertEquals(enemy.healthPoints, 50)

  test("An enemy that lost health points is still equal to an unhurt one"):
    val other = new Enemy("Goblin", 50, 5, 30, 12)
    enemy.receiveDamage(10)
    assertEquals(enemy, other)