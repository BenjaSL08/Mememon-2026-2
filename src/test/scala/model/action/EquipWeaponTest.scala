package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Sword

class EquipWeaponTest extends munit.FunSuite:

  test("Equipping a weapon is an action named Equip weapon"):
    val action: Action = new EquipWeapon(List())
    assertEquals(action.name, "Equip weapon")

  test("Equipping a weapon has the list of weapons it could equip"):
    val sword = new Sword("Excalibur", 30, 10)
    val staff = new Staff("Healing Staff", 10, 8, 30)
    val action: ItemAction = new EquipWeapon(List(sword, staff))
    assertEquals(action.items, List(sword, staff))

  test("Equipping a weapon can have no weapons to equip"):
    val action: ItemAction = new EquipWeapon(List())
    assertEquals(action.items, List())