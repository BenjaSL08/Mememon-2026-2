package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.action.InvalidActionCheck
import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Bow
import cl.uchile.dcc.model.item.weapon.Dagger
import cl.uchile.dcc.model.item.weapon.NoWeapon
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.item.weapon.Wand
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.GameUnit

import scala.collection.mutable

class KnightTest extends munit.FunSuite:
  var knight: Knight = null
  var sword: Sword = null
  var dagger: Dagger = null
  var bow: Bow = null
  var wand: Wand = null
  var staff: Staff = null

  override def beforeEach(context: BeforeEach): Unit =
    knight = new Knight("Garen", 100, 20, 80)
    sword = new Sword("DEMACIA", 30, 10)
    dagger = new Dagger("Kunai", 20, 5)
    bow = new Bow("Longbow", 25, 8)
    wand = new Wand("Elder Wand", 10, 4, 30)
    staff = new Staff("Healing Staff", 15, 9, 40)

  /** Returns the names of the actions of the character under test. */
  private def actionNames: List[String] =
    val names = mutable.ListBuffer[String]()
    for action <- knight.actions do names += action.name
    names.toList

  test("A knight has a name, health points, defense and weight"):
    assertEquals(knight.name, "Garen")
    assertEquals(knight.maxHealthPoints, 100)
    assertEquals(knight.healthPoints, 100)
    assertEquals(knight.defense, 20)
    assertEquals(knight.weight, 80)

  test("A knight with health points is alive"):
    assertEquals(knight.isAlive, true)

  test("A knight with zero health points is not alive"):
    assertEquals(new Knight("Garen", 0, 20, 80).isAlive, false)

  test("A knight starts with an empty weapon slot"):
    assertEquals(knight.weapon, NoWeapon)

  test("A knight starts with an empty inventory"):
    assertEquals(knight.inventory, List())

  test("A knight keeps its weapons first and then its potions in its inventory"):
    val potion = new HealingPotion("Potion")
    knight.addItem(potion)
    knight.addItem(sword)
    assertEquals(knight.inventory, List(sword, potion))
    assertEquals(knight.weapons, List(sword))
    assertEquals(knight.potions, List(potion))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = knight.inventory
    knight.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("A knight can equip a sword, a dagger and a bow"):
    knight.addItem(sword)
    knight.addItem(dagger)
    knight.addItem(bow)
    knight.doAction(new EquipWeapon(knight.weapons), sword)
    assertEquals(knight.weapon, sword)
    assertEquals(sword.owner, Some(knight))
    knight.doAction(new EquipWeapon(knight.weapons), dagger)
    assertEquals(knight.weapon, dagger)
    assertEquals(dagger.owner, Some(knight))
    knight.doAction(new EquipWeapon(knight.weapons), bow)
    assertEquals(knight.weapon, bow)
    assertEquals(bow.owner, Some(knight))

  test("A knight cannot equip a wand or a staff"):
    knight.addItem(wand)
    knight.addItem(staff)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(new EquipWeapon(knight.weapons), wand)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(new EquipWeapon(knight.weapons), staff)), true)
    assertEquals(knight.weapon, NoWeapon)
    assertEquals(wand.owner, None)
    assertEquals(staff.owner, None)

  test("Equipping a new weapon releases the previous one"):
    knight.addItem(sword)
    knight.addItem(dagger)
    knight.doAction(new EquipWeapon(knight.weapons), sword)
    knight.doAction(new EquipWeapon(knight.weapons), dagger)
    assertEquals(knight.weapon, dagger)
    assertEquals(sword.owner, None)
    assertEquals(dagger.owner, Some(knight))

  test("A knight cannot equip a weapon that is not in its inventory"):
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(new EquipWeapon(List(sword)), sword)), true)
    assertEquals(knight.weapon, NoWeapon)

  test("The action bar of a knight grows with half the weight of its weapon"):
    assertEquals(knight.maxActionBar, 80.0)
    knight.addItem(sword)
    knight.doAction(new EquipWeapon(knight.weapons), sword)
    assertEquals(knight.maxActionBar, 80 + 0.5 * sword.weight)

  test("A knight can attack, move, equip a weapon and consume a potion"):
    assertEquals(actionNames, List("Attack", "Move", "Equip weapon", "Consume potion"))

  test("A knight finds its actions by name"):
    assertEquals(knight.findAction("Move").get.name, "Move")
    assertEquals(knight.findAction("Fly"), None)

  test("Two knights with the same attributes are equal"):
    val other = new Knight("Garen", 100, 20, 80)
    assertEquals(knight, other)
    assertEquals(other, knight)

  test("Two knights with a different attribute are not equal"):
    assertNotEquals(knight, new Knight("Darius", 100, 20, 80))
    assertNotEquals(knight, new Knight("Garen", 105, 20, 80))
    assertNotEquals(knight, new Knight("Garen", 100, 25, 80))
    assertNotEquals(knight, new Knight("Garen", 100, 20, 85))

  test("A knight that lost health points is still equal to an unhurt one"):
    val other = new Knight("Garen", 100, 20, 80)
    knight.receiveDamage(10)
    assertEquals(knight, other)

  test("A knight is not equal to an archer with the same attributes"):
    val other: GameUnit = new Archer("Garen", 100, 20, 80)
    assertNotEquals(other, knight)

  test("A knight is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Garen", 100, 20, 80, 10)
    assertNotEquals(enemy, knight)