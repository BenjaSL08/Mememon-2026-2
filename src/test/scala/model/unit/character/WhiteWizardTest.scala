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

class WhiteWizardTest extends munit.FunSuite:
  var whiteWizard: WhiteWizard = null
  var sword: Sword = null
  var dagger: Dagger = null
  var bow: Bow = null
  var wand: Wand = null
  var staff: Staff = null

  override def beforeEach(context: BeforeEach): Unit =
    whiteWizard = new WhiteWizard("Lux", 65, 6, 45, 60)
    sword = new Sword("DEMACIA", 30, 10)
    dagger = new Dagger("Kunai", 20, 5)
    bow = new Bow("Longbow", 25, 8)
    wand = new Wand("Elder Wand", 10, 4, 30)
    staff = new Staff("Healing Staff", 15, 9, 40)

  /** Returns the names of the actions of the character under test. */
  private def actionNames: List[String] =
    val names = mutable.ListBuffer[String]()
    for action <- whiteWizard.actions do names += action.name
    names.toList

  test("A white wizard has a name, health points, defense, weight and mana"):
    assertEquals(whiteWizard.name, "Lux")
    assertEquals(whiteWizard.maxHealthPoints, 65)
    assertEquals(whiteWizard.healthPoints, 65)
    assertEquals(whiteWizard.defense, 6)
    assertEquals(whiteWizard.weight, 45)
    assertEquals(whiteWizard.maxMana, 60)
    assertEquals(whiteWizard.mana, 60)

  test("A white wizard with health points is alive"):
    assertEquals(whiteWizard.isAlive, true)

  test("A white wizard with zero health points is not alive"):
    assertEquals(new WhiteWizard("Lux", 0, 6, 45, 60).isAlive, false)

  test("A white wizard starts with an empty weapon slot"):
    assertEquals(whiteWizard.weapon, NoWeapon)

  test("A white wizard starts with an empty inventory"):
    assertEquals(whiteWizard.inventory, List())

  test("A white wizard keeps its weapons first and then its potions in its inventory"):
    val potion = new HealingPotion("Potion")
    whiteWizard.addItem(potion)
    whiteWizard.addItem(sword)
    assertEquals(whiteWizard.inventory, List(sword, potion))
    assertEquals(whiteWizard.weapons, List(sword))
    assertEquals(whiteWizard.potions, List(potion))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = whiteWizard.inventory
    whiteWizard.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("A white wizard can equip a bow, a wand and a staff"):
    whiteWizard.addItem(bow)
    whiteWizard.addItem(wand)
    whiteWizard.addItem(staff)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), bow)
    assertEquals(whiteWizard.weapon, bow)
    assertEquals(bow.owner, Some(whiteWizard))
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), wand)
    assertEquals(whiteWizard.weapon, wand)
    assertEquals(wand.owner, Some(whiteWizard))
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), staff)
    assertEquals(whiteWizard.weapon, staff)
    assertEquals(staff.owner, Some(whiteWizard))

  test("A white wizard cannot equip a sword or a dagger"):
    whiteWizard.addItem(sword)
    whiteWizard.addItem(dagger)
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), sword)), true)
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), dagger)), true)
    assertEquals(whiteWizard.weapon, NoWeapon)
    assertEquals(sword.owner, None)
    assertEquals(dagger.owner, None)

  test("Equipping a new weapon releases the previous one"):
    whiteWizard.addItem(bow)
    whiteWizard.addItem(wand)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), bow)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), wand)
    assertEquals(whiteWizard.weapon, wand)
    assertEquals(bow.owner, None)
    assertEquals(wand.owner, Some(whiteWizard))

  test("A white wizard cannot equip a weapon that is not in its inventory"):
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.doAction(new EquipWeapon(List(bow)), bow)), true)
    assertEquals(whiteWizard.weapon, NoWeapon)

  test("The action bar of a white wizard grows with half the weight of its weapon"):
    assertEquals(whiteWizard.maxActionBar, 45.0)
    whiteWizard.addItem(bow)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), bow)
    assertEquals(whiteWizard.maxActionBar, 45 + 0.5 * bow.weight)

  test("A white wizard can do every action of a character, plus Healing and Purification"):
    assertEquals(actionNames, List("Attack", "Move", "Equip weapon", "Consume potion", "Healing", "Purification"))

  test("A white wizard finds its actions by name"):
    assertEquals(whiteWizard.findAction("Move").get.name, "Move")
    assertEquals(whiteWizard.findAction("Fly"), None)

  test("Casting a spell spends mana and returns the magic damage of the weapon"):
    whiteWizard.addItem(wand)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), wand)
    assertEquals(whiteWizard.castSpell(20), 30)
    assertEquals(whiteWizard.mana, 40)

  test("A white wizard without enough mana cannot cast a spell"):
    whiteWizard.addItem(wand)
    whiteWizard.doAction(new EquipWeapon(whiteWizard.weapons), wand)
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.castSpell(61)), true)
    assertEquals(whiteWizard.mana, 60)

  test("A white wizard without a magic weapon cannot cast a spell"):
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.castSpell(20)), true)
    whiteWizard.addItem(sword)
    assertEquals(InvalidActionCheck.fails(() => whiteWizard.castSpell(20)), true)
    assertEquals(whiteWizard.mana, 60)

  test("Two white wizards with the same attributes are equal"):
    val other = new WhiteWizard("Lux", 65, 6, 45, 60)
    assertEquals(whiteWizard, other)
    assertEquals(other, whiteWizard)

  test("Two white wizards with a different attribute are not equal"):
    assertNotEquals(whiteWizard, new WhiteWizard("Soraka", 65, 6, 45, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Lux", 70, 6, 45, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Lux", 65, 11, 45, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Lux", 65, 6, 50, 60))
    assertNotEquals(whiteWizard, new WhiteWizard("Lux", 65, 6, 45, 65))

  test("A white wizard that lost health points is still equal to an unhurt one"):
    val other = new WhiteWizard("Lux", 65, 6, 45, 60)
    whiteWizard.receiveDamage(10)
    assertEquals(whiteWizard, other)

  test("A white wizard is not equal to a black wizard with the same attributes"):
    val other: GameUnit = new BlackWizard("Lux", 65, 6, 45, 60)
    assertNotEquals(other, whiteWizard)

  test("A white wizard is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Lux", 65, 6, 45, 10)
    assertNotEquals(enemy, whiteWizard)