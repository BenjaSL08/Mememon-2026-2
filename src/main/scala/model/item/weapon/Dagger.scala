package cl.uchile.dcc
package model.item.weapon

/**
 * A dagger, one common weapon.
 *
 * @param name the name of the weapon.
 * @param attack the attack points of the weapon.
 * @param weight the weight of the weapon.
 */
class Dagger(name: String, attack: Int, weight: Int)
  extends AbstractWeapon(name, attack, weight)
