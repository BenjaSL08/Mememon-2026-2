package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.potion.HealingPotion
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Sword
import cl.uchile.dcc.model.panel.GridPanel
import cl.uchile.dcc.model.unit.Enemy
import cl.uchile.dcc.model.unit.character.Knight

class EquipWeaponTest extends munit.FunSuite:

  test("Equipping a weapon is an action named Equip weapon"):
    val action: Action = new EquipWeapon(List())
    assertEquals(action.name, "Equip weapon")

  test("Equipping a weapon has the list of weapons it could equip"):
    val sword = new Sword("Diamond Sword", 25, 10)
    val staff = new Staff("Healer Staff", 10, 8, 30)
    val action: ItemAction = new EquipWeapon(List(sword, staff))
    assertEquals(action.items, List(sword, staff))

  test("Equipping a weapon can have no weapons to equip"):
    val action: ItemAction = new EquipWeapon(List())
    assertEquals(action.items, List())

  test("An enemy cannot equip a weapon"):
    val enemy = new Enemy("Goblin", 50, 5, 30, 12)
    val sword = new Sword("Diamond Sword", 25, 10)
    assertEquals(InvalidActionCheck.fails(() => enemy.doAction(new EquipWeapon(List(sword)), sword)), true)
    assertEquals(sword.owner, None)

  test("Only weapons can be equipped"):
    val knight = new Knight("Garen", 100, 20, 80)
    val potion = new HealingPotion("Potion")
    knight.addItem(potion)
    val action = new EquipWeapon(knight.weapons)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(action, potion)), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(action, new GridPanel(1, 1))), true)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(action, knight)), true)