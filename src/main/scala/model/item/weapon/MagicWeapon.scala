package cl.uchile.dcc
package model.item.weapon

/**
 * A weapon that also has magic attack points, used to calculate the damage
 * of spells.
 */
trait MagicWeapon extends Weapon:

  /** Returns the magic attack points of this weapon. */
  def magicAttack: Int