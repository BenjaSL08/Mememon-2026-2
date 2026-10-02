package cl.uchile.dcc
package model.item.weapon

/**
 * A bow, one common weapon.
 *
 * @param name the name of the weapon.
 * @param attack the attack points of the weapon.
 * @param weight the weight of the weapon.
 */
class Bow(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)