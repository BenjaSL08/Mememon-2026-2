package cl.uchile.dcc
package model.item.weapon

import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * A staff, one of the magic weapons.
 *
 * @param name the name of the weapon.
 * @param attack the attack points of the weapon.
 * @param weight the weight of the weapon.
 * @param magicAttack the magic attack points of the weapon.
 */
class Staff(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack):

  /** The character decides if it can equip a staff. */
  override def receiveEquip(character: GameCharacter): Unit = character.equipStaff(this)