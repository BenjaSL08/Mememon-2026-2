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

class BlackWizardTest extends munit.FunSuite:
  var blackWizard: BlackWizard = null
  var sword: Sword = null
  var dagger: Dagger = null
  var bow: Bow = null
  var wand: Wand = null
  var staff: Staff = null

  override def beforeEach(context: BeforeEach): Unit =
    blackWizard = new BlackWizard("Ryze", 60, 5, 40, 50)
    sword = new Sword("Diamond Sword", 30, 10)
    dagger = new Dagger("Kunai", 20, 5)
    bow = new Bow("Longbow", 25, 8)
    wand = new Wand("Elder Wand", 10, 4, 30)
    staff = new Staff("Healing Staff", 15, 9, 40)

  /** Returns the names of the actions of the character under test. */
  private def actionNames: List[String] =
    val names = mutable.ListBuffer[String]()
    for action <- blackWizard.actions do names += action.name
    names.toList

  test("A black wizard has a name, health points, defense, weight and mana"):
    assertEquals(blackWizard.name, "Ryze")
    assertEquals(blackWizard.maxHealthPoints, 60)
    assertEquals(blackWizard.healthPoints, 60)
    assertEquals(blackWizard.defense, 5)
    assertEquals(blackWizard.weight, 40)
    assertEquals(blackWizard.maxMana, 50)
    assertEquals(blackWizard.mana, 50)

  test("A black wizard with health points is alive"):
    assertEquals(blackWizard.isAlive, true)

  test("A black wizard with zero health points is not alive"):
    assertEquals(new BlackWizard("Ryze", 0, 5, 40, 50).isAlive, false)

  test("A black wizard starts with an empty weapon slot"):
    assertEquals(blackWizard.weapon, NoWeapon)

  test("A black wizard starts with an empty inventory"):
    assertEquals(blackWizard.inventory, List())

  test("A black wizard keeps its weapons first and then its potions in its inventory"):
    val potion = new HealingPotion("Potion")
    blackWizard.addItem(potion)
    blackWizard.addItem(sword)
    assertEquals(blackWizard.inventory, List(sword, potion))
    assertEquals(blackWizard.weapons, List(sword))
    assertEquals(blackWizard.potions, List(potion))

  test("Adding an item does not change an inventory obtained before"):
    val inventoryBefore = blackWizard.inventory
    blackWizard.addItem(new HealingPotion("Potion"))
    assertEquals(inventoryBefore, List())

  test("A black wizard can equip a dagger, a wand and a staff"):
    blackWizard.addItem(dagger)
    blackWizard.addItem(wand)
    blackWizard.addItem(staff)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), dagger)
    assertEquals(blackWizard.weapon, dagger)
    assertEquals(dagger.owner, Some(blackWizard))
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), wand)
    assertEquals(blackWizard.weapon, wand)
    assertEquals(wand.owner, Some(blackWizard))
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), staff)
    assertEquals(blackWizard.weapon, staff)
    assertEquals(staff.owner, Some(blackWizard))

  test("A black wizard cannot equip a sword or a bow"):
    blackWizard.addItem(sword)
    blackWizard.addItem(bow)
    assertEquals(InvalidActionCheck.fails(() => blackWizard.doAction(new EquipWeapon(blackWizard.weapons), sword)), true)
    assertEquals(InvalidActionCheck.fails(() => blackWizard.doAction(new EquipWeapon(blackWizard.weapons), bow)), true)
    assertEquals(blackWizard.weapon, NoWeapon)
    assertEquals(sword.owner, None)
    assertEquals(bow.owner, None)

  test("Equipping a new weapon releases the previous one"):
    blackWizard.addItem(dagger)
    blackWizard.addItem(wand)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), dagger)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), wand)
    assertEquals(blackWizard.weapon, wand)
    assertEquals(dagger.owner, None)
    assertEquals(wand.owner, Some(blackWizard))

  test("A black wizard cannot equip a weapon that is not in its inventory"):
    assertEquals(InvalidActionCheck.fails(() => blackWizard.doAction(new EquipWeapon(List(dagger)), dagger)), true)
    assertEquals(blackWizard.weapon, NoWeapon)

  test("The action bar of a black wizard grows with half the weight of its weapon"):
    assertEquals(blackWizard.maxActionBar, 40.0)
    blackWizard.addItem(dagger)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), dagger)
    assertEquals(blackWizard.maxActionBar, 40 + 0.5 * dagger.weight)

  test("A black wizard can do every action of a character, plus Thunder and Meteor"):
    assertEquals(actionNames, List("Attack", "Move", "Equip weapon", "Consume potion", "Thunder", "Meteor"))

  test("A black wizard finds its actions by name"):
    assertEquals(blackWizard.findAction("Move").get.name, "Move")
    assertEquals(blackWizard.findAction("Fly"), None)

  test("Casting a spell spends mana and returns the magic damage of the weapon"):
    blackWizard.addItem(wand)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), wand)
    assertEquals(blackWizard.castSpell(20), 30)
    assertEquals(blackWizard.mana, 30)

  test("A black wizard without enough mana cannot cast a spell"):
    blackWizard.addItem(wand)
    blackWizard.doAction(new EquipWeapon(blackWizard.weapons), wand)
    assertEquals(InvalidActionCheck.fails(() => blackWizard.castSpell(51)), true)
    assertEquals(blackWizard.mana, 50)

  test("A black wizard without a magic weapon cannot cast a spell"):
    assertEquals(InvalidActionCheck.fails(() => blackWizard.castSpell(20)), true)
    blackWizard.addItem(sword)
    assertEquals(InvalidActionCheck.fails(() => blackWizard.castSpell(20)), true)
    assertEquals(blackWizard.mana, 50)

  test("Two black wizards with the same attributes are equal"):
    val other = new BlackWizard("Ryze", 60, 5, 40, 50)
    assertEquals(blackWizard, other)
    assertEquals(other, blackWizard)

  test("Two black wizards with a different attribute are not equal"):
    assertNotEquals(blackWizard, new BlackWizard("Veigar", 60, 5, 40, 50))
    assertNotEquals(blackWizard, new BlackWizard("Ryze", 65, 5, 40, 50))
    assertNotEquals(blackWizard, new BlackWizard("Ryze", 60, 10, 40, 50))
    assertNotEquals(blackWizard, new BlackWizard("Ryze", 60, 5, 45, 50))
    assertNotEquals(blackWizard, new BlackWizard("Ryze", 60, 5, 40, 55))

  test("A black wizard that lost health points is still equal to an unhurt one"):
    val other = new BlackWizard("Ryze", 60, 5, 40, 50)
    blackWizard.receiveDamage(10)
    assertEquals(blackWizard, other)

  test("A black wizard is not equal to a white wizard with the same attributes"):
    val other: GameUnit = new WhiteWizard("Ryze", 60, 5, 40, 50)
    assertNotEquals(other, blackWizard)

  test("A black wizard is not equal to an enemy with the same attributes"):
    val enemy: GameUnit = new Enemy("Ryze", 60, 5, 40, 10)
    assertNotEquals(enemy, blackWizard)