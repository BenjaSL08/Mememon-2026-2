package cl.uchile.dcc
package model.item.weapon

/**
 * Base implementation shared by every [[MagicWeapon]].
 *
 * @param name the name of the weapon.
 * @param attack the attack points of the weapon.
 * @param weight the weight of the weapon.
 * @param _magicAttack the magic attack points of the weapon.
 */
abstract class AbstractMagicWeapon(name: String, attack: Int, weight: Int,
                                   private val _magicAttack: Int)
  extends AbstractWeapon(name, attack, weight), MagicWeapon:

  override def magicAttack: Int = _magicAttack

  /**
   * Compares this magic weapon with another object.
   *
   * Two magic weapons are equal when they share all the attributes of a
   * weapon and have the same magic attack points.
   *
   * @param obj the object to compare with.
   * @return `true` if both weapons are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    super.equals(obj) && magicAttack == obj.asInstanceOf[AbstractMagicWeapon].magicAttack