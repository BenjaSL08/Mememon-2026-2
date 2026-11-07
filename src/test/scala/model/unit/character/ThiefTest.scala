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

class ThiefTest extends munit.FunSuite:
  var thief: Thief = null
  var sword: Sword = null
  var dagger: Dagger = null
  var bow: Bow = null
  var wand: Wand = null
  var staff: Staff = null

  override def beforeEach(context: BeforeEach): Unit =
    thief = new Thief("Locke", 70, 8, 40)
    sword = new Sword("Zhonias", 30, 10)
    dagger = new Dagger("Kunai", 20, 5)
    bow = new Bow("Longbow", 25, 8)
    wand = new Wand("Elder Wand", 10, 4, 30)
    staff = new Staff("Healing Staff", 15, 9, 40)

  /** Returns the names of the actions of the character under test. */
  private def actionNames: List[String] =
    val names = mutable.ListBuffer[String]()
    for action <- thief.actions do names += action.name
    names.toList

  test("A thief has a name, health points, defense and weight"):
    assertEquals(thief.name, "Locke")
    assertEquals(thief.maxHealthPoints, 70)
    assertEquals(thief.healthPoints, 70)
    assertEquals(thief.defense, 8)
    assertEquals(thief.weight, 40)

  test("A thief with health points is alive"):
    assertEquals(thief.isAlive, true)

  test("A thief with zero health points is not alive"):
    assertEquals(new Thief("Locke", 0, 8, 40).isAlive, false)

  test("A thief starts with an empty weapon slot"):
    assertEquals(thief.weapon, NoWeapon)

  test("A thief starts with an empty inventory"):
    assertEquals(thief.inventory, List())

  test("A thief keeps its weapons first and then its potions in its inventory"):
    val potion = new HealingPotion("Potion")
    thief.addItem(potion)
    thief.addItem(sword)
    assertEquals(thief.inventory, List(sword, potion))
    assertEquals(thief.weapons, List(sword))
    assertEquals(thief.potions, List(potion))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = thief.inventory
    thief.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("A thief can equip a sword and a dagger"):
    thief.addItem(sword)
    thief.addItem(dagger)
    thief.doAction(new EquipWeapon(thief.weapons), sword)
    assertEquals(thief.weapon, sword)
    assertEquals(sword.owner, Some(thief))
    thief.doAction(new EquipWeapon(thief.weapons), dagger)
    assertEquals(thief.weapon, dagger)
    assertEquals(dagger.owner, Some(thief))

  test("A thief cannot equip a bow, a wand or a staff"):
    thief.addItem(bow)
    thief.addItem(wand)
    thief.addItem(staff)
    assertEquals(InvalidActionCheck.fails(() => thief.doAction(new EquipWeapon(thief.weapons), bow)), true)
    assertEquals(InvalidActionCheck.fails(() => thief.doAction(new EquipWeapon(thief.weapons), wand)), true)
    assertEquals(InvalidActionCheck.fails(() => thief.doAction(new EquipWeapon(thief.weapons), staff)), true)
    assertEquals(thief.weapon, NoWeapon)
    assertEquals(bow.owner, None)
    assertEquals(wand.owner, None)
    assertEquals(staff.owner, None)

  test("Equipping a new weapon releases the previous one"):
    thief.addItem(sword)
    thief.addItem(dagger)
    thief.doAction(new EquipWeapon(thief.weapons), sword)
    thief.doAction(new EquipWeapon(thief.weapons), dagger)
    assertEquals(thief.weapon, dagger)
    assertEquals(sword.owner, None)
    assertEquals(dagger.owner, Some(thief))

  test("A thief cannot equip a weapon that is not in its inventory"):
    assertEquals(InvalidActionCheck.fails(() => thief.doAction(new EquipWeapon(List(sword)), sword)), true)
    assertEquals(thief.weapon, NoWeapon)

  test("The action bar of a thief grows with half the weight of its weapon"):
    assertEquals(thief.maxActionBar, 40.0)
    thief.addItem(sword)
    thief.doAction(new EquipWeapon(thief.weapons), sword)
    assertEquals(thief.maxActionBar, 40 + 0.5 * sword.weight)

  test("A thief can attack, move, equip a weapon and consume a potion"):
    assertEquals(actionNames, List("Attack", "Move", "Equip weapon", "Consume potion"))

  test("A thief finds its actions by name"):
    assertEquals(thief.findAction("Move").get.name, "Move")
    assertEquals(thief.findAction("Fly"), None)

  test("Two thieves with the same attributes are equal"):
    val other = new Thief("Locke", 70, 8, 40)
    assertEquals(thief, other)
    assertEquals(other, thief)

  test("Two thieves with a different attribute are not equal"):
    assertNotEquals(thief, new Thief("Twitch", 70, 8, 40))
    assertNotEquals(thief, new Thief("Locke", 75, 8, 40))
    assertNotEquals(thief, new Thief("Locke", 70, 13, 40))
    assertNotEquals(thief, new Thief("Locke", 70, 8, 45))

  test("A thief that lost health points is still equal to an unhurt one"):
    val other = new Thief("Locke", 70, 8, 40)
    thief.receiveDamage(10)
    assertEquals(thief, other)

  test("A thief is not equal to a knight with the same attributes"):
    val other: GameUnit = new Knight("Locke", 70, 8, 40)
    assertNotEquals(other, thief)

  test("A thief is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Locke", 70, 8, 40, 10)
    assertNotEquals(enemy, thief)