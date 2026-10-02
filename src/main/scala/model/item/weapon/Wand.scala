package cl.uchile.dcc
package model.item.weapon

/**
 * A wand, one of the magic weapons.
 *
 * @param name the name of the weapon.
 * @param attack the attack points of the weapon.
 * @param weight the weight of the weapon.
 * @param magicAttack the magic attack points of the weapon.
 */
class Wand(name: String, attack: Int, weight: Int, magicAttack: Int)
  extends AbstractMagicWeapon(name, attack, weight, magicAttack)