package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.action.Action
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.action.spell.Healing
import cl.uchile.dcc.model.action.spell.Purification
import cl.uchile.dcc.model.item.weapon.Bow
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Wand

import scala.collection.mutable

/**
 * A white wizard, a magic character that casts white magic spells and can
 * equip bows, wands and staffs.
 *
 * @param name the name of the character.
 * @param maxHealthPoints the maximum health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 * @param mana the maximum mana points of the character.
 */
class WhiteWizard(name: String, maxHealthPoints: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, maxHealthPoints, defense, weight, mana):

  /** A white wizard can do every action of a character, plus Healing and Purification. */
  override def actions: List[Action] =
    val all = mutable.ListBuffer[Action]()
    for action <- super.actions do all += action
    all += new Healing()
    all += new Purification()
    all.toList

  override protected def dispatchTo(action: Action, target: Target): Unit =
    action.doByWhiteWizard(this, target)

  override def equipBow(bow: Bow): Unit = equip(bow)

  override def equipWand(wand: Wand): Unit = equip(wand)

  override def equipStaff(staff: Staff): Unit = equip(staff)