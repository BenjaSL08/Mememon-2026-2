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

class ArcherTest extends munit.FunSuite:
  var archer: Archer = null
  var sword: Sword = null
  var dagger: Dagger = null
  var bow: Bow = null
  var wand: Wand = null
  var staff: Staff = null

  override def beforeEach(context: BeforeEach): Unit =
    archer = new Archer("Ashe", 80, 10, 50)
    sword = new Sword("Diamond Sword", 30, 10)
    dagger = new Dagger("Kunai", 20, 5)
    bow = new Bow("Longbow", 25, 8)
    wand = new Wand("Elder Wand", 10, 4, 30)
    staff = new Staff("Healing Staff", 15, 9, 40)

  /** Returns the names of the actions of the character under test. */
  private def actionNames: List[String] =
    val names = mutable.ListBuffer[String]()
    for action <- archer.actions do names += action.name
    names.toList

  test("An archer has a name, health points, defense and weight"):
    assertEquals(archer.name, "Ashe")
    assertEquals(archer.maxHealthPoints, 80)
    assertEquals(archer.healthPoints, 80)
    assertEquals(archer.defense, 10)
    assertEquals(archer.weight, 50)

  test("An archer with health points is alive"):
    assertEquals(archer.isAlive, true)

  test("An archer with zero health points is not alive"):
    assertEquals(new Archer("Ashe", 0, 10, 50).isAlive, false)

  test("An archer starts with an empty weapon slot"):
    assertEquals(archer.weapon, NoWeapon)

  test("An archer starts with an empty inventory"):
    assertEquals(archer.inventory, List())

  test("An archer keeps its weapons first and then its potions in its inventory"):
    val potion = new HealingPotion("Potion")
    archer.addItem(potion)
    archer.addItem(sword)
    assertEquals(archer.inventory, List(sword, potion))
    assertEquals(archer.weapons, List(sword))
    assertEquals(archer.potions, List(potion))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = archer.inventory
    archer.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("An archer can equip a sword, a bow and a wand"):
    archer.addItem(sword)
    archer.addItem(bow)
    archer.addItem(wand)
    archer.doAction(new EquipWeapon(archer.weapons), sword)
    assertEquals(archer.weapon, sword)
    assertEquals(sword.owner, Some(archer))
    archer.doAction(new EquipWeapon(archer.weapons), bow)
    assertEquals(archer.weapon, bow)
    assertEquals(bow.owner, Some(archer))
    archer.doAction(new EquipWeapon(archer.weapons), wand)
    assertEquals(archer.weapon, wand)
    assertEquals(wand.owner, Some(archer))

  test("An archer cannot equip a dagger or a staff"):
    archer.addItem(dagger)
    archer.addItem(staff)
    assertEquals(InvalidActionCheck.fails(() => archer.doAction(new EquipWeapon(archer.weapons), dagger)), true)
    assertEquals(InvalidActionCheck.fails(() => archer.doAction(new EquipWeapon(archer.weapons), staff)), true)
    assertEquals(archer.weapon, NoWeapon)
    assertEquals(dagger.owner, None)
    assertEquals(staff.owner, None)

  test("Equipping a new weapon releases the previous one"):
    archer.addItem(sword)
    archer.addItem(bow)
    archer.doAction(new EquipWeapon(archer.weapons), sword)
    archer.doAction(new EquipWeapon(archer.weapons), bow)
    assertEquals(archer.weapon, bow)
    assertEquals(sword.owner, None)
    assertEquals(bow.owner, Some(archer))

  test("An archer cannot equip a weapon that is not in its inventory"):
    assertEquals(InvalidActionCheck.fails(() => archer.doAction(new EquipWeapon(List(sword)), sword)), true)
    assertEquals(archer.weapon, NoWeapon)

  test("The action bar of an archer grows with half the weight of its weapon"):
    assertEquals(archer.maxActionBar, 50.0)
    archer.addItem(sword)
    archer.doAction(new EquipWeapon(archer.weapons), sword)
    assertEquals(archer.maxActionBar, 50 + 0.5 * sword.weight)

  test("An archer can attack, move, equip a weapon and consume a potion"):
    assertEquals(actionNames, List("Attack", "Move", "Equip weapon", "Consume potion"))

  test("An archer finds its actions by name"):
    assertEquals(archer.findAction("Move").get.name, "Move")
    assertEquals(archer.findAction("Fly"), None)

  test("Two archers with the same attributes are equal"):
    val other = new Archer("Ashe", 80, 10, 50)
    assertEquals(archer, other)
    assertEquals(other, archer)

  test("Two archers with a different attribute are not equal"):
    assertNotEquals(archer, new Archer("Caitlyn", 80, 10, 50))
    assertNotEquals(archer, new Archer("Ashe", 85, 10, 50))
    assertNotEquals(archer, new Archer("Ashe", 80, 15, 50))
    assertNotEquals(archer, new Archer("Ashe", 80, 10, 55))

  test("An archer that lost health points is still equal to an unhurt one"):
    val other = new Archer("Ashe", 80, 10, 50)
    archer.receiveDamage(10)
    assertEquals(archer, other)

  test("An archer is not equal to a thief with the same attributes"):
    val other: GameUnit = new Thief("Ashe", 80, 10, 50)
    assertNotEquals(other, archer)

  test("An archer is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Ashe", 80, 10, 50, 10)
    assertNotEquals(enemy, archer)