package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.potion.FortitudePotion
import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.potion.MagicStrengthPotion
import cl.uchile.dcc.model.item.potion.ManaPotion
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.item.weapon.Wand
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.BlackWizard
import cl.uchile.dcc.model.unit.character.Knight

class ConsumePotionTest extends munit.FunSuite:
  var knight: Knight = null
  var wizard: BlackWizard = null

  override def beforeEach(context: BeforeEach): Unit =
    knight = new Knight("Garen", 100, 20, 80)
    wizard = new BlackWizard("Ryze", 60, 5, 40, 50)
    val wand = new Wand("Elder Wand", 10, 4, 30)
    wizard.addItem(wand)
    wizard.doAction(new EquipWeapon(wizard.weapons), wand)

  /** Makes a character consume a potion from its inventory. */
  private def consume(character: Knight, potion: HealingPotion): Unit =
    character.doAction(new ConsumePotion(character.potions), potion)

  test("Consuming a potion is an action named Consume potion"):
    val action: Action = new ConsumePotion(List())
    assertEquals(action.name, "Consume potion")

  test("Consuming a potion has the list of potions it could consume"):
    val healing = new HealingPotion("Life Potion")
    val mana = new ManaPotion("Mana Potion")
    val action: ItemAction = new ConsumePotion(List(healing, mana))
    assertEquals(action.items, List(healing, mana))

  test("Consuming a potion can have no potions to consume"):
    val action: ItemAction = new ConsumePotion(List())
    assertEquals(action.items, List())

  test("A healing potion restores 20% of the maximum health points and leaves the inventory"):
    val potion = new HealingPotion("Potion")
    knight.addItem(potion)
    knight.receiveDamage(50)
    consume(knight, potion)
    assertEquals(knight.healthPoints, 70)
    assertEquals(knight.potions, List())

  test("A healing potion never heals above the maximum health points"):
    val potion = new HealingPotion("Potion")
    knight.addItem(potion)
    knight.receiveDamage(10)
    consume(knight, potion)
    assertEquals(knight.healthPoints, 100)

  test("A fortitude potion increases the defense by 15%"):
    val potion = new FortitudePotion("Giant Draught")
    knight.addItem(potion)
    knight.doAction(new ConsumePotion(knight.potions), potion)
    assertEquals(knight.defense, 23)

  test("Consuming one of two equal potions leaves the other one"):
    val potion = new HealingPotion("Potion")
    val other = new HealingPotion("Potion")
    knight.addItem(potion)
    knight.addItem(other)
    consume(knight, potion)
    assertEquals(knight.potions, List(other))

  test("A common character cannot consume magic potions"):
    val mana = new ManaPotion("Ether")
    val strength = new MagicStrengthPotion("Arcane Tonic")
    knight.addItem(mana)
    knight.addItem(strength)
    val action = new ConsumePotion(knight.potions)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(action, mana)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(action, strength)), true)
    assertEquals(knight.potions, List(mana, strength))

  test("A mana potion restores 30% of the maximum mana of a magic character"):
    val potion = new ManaPotion("Ether")
    wizard.addItem(potion)
    wizard.castSpell(40)
    wizard.doAction(new ConsumePotion(wizard.potions), potion)
    assertEquals(wizard.mana, 25)

  test("A mana potion never restores mana above the maximum"):
    val potion = new ManaPotion("Ether")
    wizard.addItem(potion)
    wizard.castSpell(10)
    wizard.doAction(new ConsumePotion(wizard.potions), potion)
    assertEquals(wizard.mana, 50)

  test("A magic strength potion increases the damage of the next spell only"):
    val potion = new MagicStrengthPotion("Arcane Tonic")
    wizard.addItem(potion)
    wizard.doAction(new ConsumePotion(wizard.potions), potion)
    assertEquals(wizard.castSpell(20), 45)
    assertEquals(wizard.castSpell(20), 30)

  test("A magic character can also consume common potions"):
    val potion = new HealingPotion("Potion")
    wizard.addItem(potion)
    wizard.receiveDamage(30)
    wizard.doAction(new ConsumePotion(wizard.potions), potion)
    assertEquals(wizard.healthPoints, 42)

  test("A character cannot consume a potion that is not in its inventory"):
    val potion = new HealingPotion("Potion")
    knight.receiveDamage(50)
    assertEquals(InvalidActionCheck.fails(() => consume(knight, potion)), true)
    assertEquals(knight.healthPoints, 50)

  test("An enemy cannot consume a potion"):
    val enemy = new Enemy("Goblin", 50, 5, 30, 12)
    val potion = new HealingPotion("Potion")
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(new ConsumePotion(List(potion)), potion)), true)

  test("Only potions can be consumed"):
    val sword = new Sword("Excalibur", 30, 10)
    knight.addItem(sword)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(new ConsumePotion(List()), sword)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(new ConsumePotion(List()), knight)), true)