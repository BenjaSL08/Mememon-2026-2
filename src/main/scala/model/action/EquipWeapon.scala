package cl.uchile.dcc
package model.action

import model.item.Item
import model.item.weapon.Weapon

/**
 * The action of equipping a weapon.
 *
 * @param _weapons the weapons this action could equip.
 */
class EquipWeapon(private val _weapons: List[Weapon])
  extends AbstractAction("Equip weapon"), ItemAction:

  override def items: List[Item] = _weapons