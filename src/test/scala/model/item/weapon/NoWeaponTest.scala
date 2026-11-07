package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.action.EquipWeapon
import cl.uchile.dcc.model.action.InvalidActionCheck
import cl.uchile.dcc.model.unit.character.Knight

class NoWeaponTest extends munit.FunSuite:

  test("The empty slot has a name, and no attack points or weight"):
    assertEquals(NoWeapon.name, "No weapon")
    assertEquals(NoWeapon.attack, 0)
    assertEquals(NoWeapon.weight, 0)

  test("The empty slot cannot be used to attack"):
    assertEquals(InvalidActionCheck.fails(() => NoWeapon.attackDamage), true)

  test("The empty slot cannot be used to cast spells"):
    assertEquals(InvalidActionCheck.fails(() => NoWeapon.magicDamage), true)

  test("The empty slot never has an owner"):
    val knight = new Knight("Garen", 100, 20, 80)
    NoWeapon.assignOwner(knight)
    assertEquals(NoWeapon.owner, None)
    NoWeapon.release()
    assertEquals(NoWeapon.owner, None)

  test("The empty slot is never stored in an inventory"):
    val knight = new Knight("Garen", 100, 20, 80)
    knight.addItem(NoWeapon)
    assertEquals(knight.inventory, List())

  test("The empty slot cannot be equipped"):
    val knight = new Knight("Garen", 100, 20, 80)
    assertEquals(InvalidActionCheck.fails(() => knight.doAction(new EquipWeapon(List()), NoWeapon)), true)