package cl.uchile.dcc
package model.action

import cl.uchile.dcc.model.item.Item
import cl.uchile.dcc.model.item.weapon.Weapon
import cl.uchile.dcc.model.unit.character.GameCharacter

/**
 * The action of equipping a weapon. Only characters can do it.
 *
 * @param _weapons the weapons this action could equip.
 */
class EquipWeapon(private val _weapons: List[Weapon])
  extends AbstractAction("Equip weapon"), ItemAction:

  override def items: List[Item] = _weapons

  override def doByCharacter(character: GameCharacter, target: Target): Unit =
    target.receiveEquip(character)