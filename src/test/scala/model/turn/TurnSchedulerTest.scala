package cl.uchile.dcc
package model.turn

import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.Knight
import cl.uchile.dcc.model.unit.character.Thief

class TurnSchedulerTest extends munit.FunSuite:
  var scheduler: TurnScheduler = null
  var knight: Knight = null
  var thief: Thief = null
  var enemy: Enemy = null

  override def beforeEach(context: BeforeEach): Unit =
    scheduler = new TurnScheduler()
    knight = new Knight("Alexis Sanchez", 100, 20, 80)
    thief = new Thief("Messi", 70, 8, 40)
    enemy = new Enemy("Faker", 50, 5, 30, 12)

  test("A scheduler starts without units"):
    assertEquals(scheduler.units, List())

  test("Units can be added to the scheduler"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    assertEquals(scheduler.units, List(knight, enemy))

  test("Units can be removed from the scheduler"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    scheduler.removeUnit(knight)
    assertEquals(scheduler.units, List(enemy))

  test("Removing a unit that is not in battle changes nothing"):
    scheduler.addUnit(knight)
    scheduler.removeUnit(enemy)
    assertEquals(scheduler.units, List(knight))

  test("The maximum action bar of a character without a weapon is its weight"):
    assertEquals(scheduler.maxActionBar(knight), 80.0)

  test("The maximum action bar of an enemy is its weight"):
    assertEquals(scheduler.maxActionBar(enemy), 30.0)

  test("An action bar starts empty"):
    scheduler.addUnit(knight)
    assertEquals(scheduler.currentActionBar(knight), 0.0)

  test("A unit that is not in battle has an empty action bar"):
    assertEquals(scheduler.currentActionBar(knight), 0.0)

  test("The action bars of all units increase at the same time"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(25)
    assertEquals(scheduler.currentActionBar(knight), 25.0)
    assertEquals(scheduler.currentActionBar(enemy), 25.0)

  test("The action bar of a unit can be reset"):
    scheduler.addUnit(knight)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(25)
    scheduler.resetActionBar(knight)
    assertEquals(scheduler.currentActionBar(knight), 0.0)
    assertEquals(scheduler.currentActionBar(enemy), 25.0)

  test("A unit below its maximum has not completed its action bar"):
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(29)
    assertEquals(scheduler.hasCompletedActionBar(enemy), false)

  test("A unit that reaches its maximum exactly has completed its action bar"):
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(30)
    assertEquals(scheduler.hasCompletedActionBar(enemy), true)

  test("A unit that is not in battle has not completed its action bar"):
    assertEquals(scheduler.hasCompletedActionBar(enemy), false)

  test("The completed units are ordered from the largest to the smallest excess"):
    scheduler.addUnit(knight)
    scheduler.addUnit(thief)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(100)
    assertEquals(scheduler.completedUnits, List(enemy, thief, knight))

  test("The completed units do not include units below their maximum"):
    scheduler.addUnit(knight)
    scheduler.addUnit(thief)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(50)
    assertEquals(scheduler.completedUnits, List(enemy, thief))

  test("Units with the same excess keep the order in which they were added"):
    val otherEnemy = new Enemy("Kanye", 60, 8, 30, 15)
    scheduler.addUnit(enemy)
    scheduler.addUnit(otherEnemy)
    scheduler.increaseActionBars(40)
    assertEquals(scheduler.completedUnits, List(enemy, otherEnemy))

  test("No unit gets the turn if no unit completed its action bar"):
    scheduler.addUnit(knight)
    scheduler.increaseActionBars(10)
    assertEquals(scheduler.nextUnit, None)

  test("The turn goes to the unit with the largest excess"):
    scheduler.addUnit(knight)
    scheduler.addUnit(thief)
    scheduler.addUnit(enemy)
    scheduler.increaseActionBars(100)
    assertEquals(scheduler.nextUnit, Some(enemy))
