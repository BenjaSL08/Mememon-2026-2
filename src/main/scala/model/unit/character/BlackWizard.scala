package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.action.Action
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.action.spell.Meteor
import cl.uchile.dcc.model.action.spell.Thunder
import cl.uchile.dcc.model.item.weapon.Dagger
import cl.uchile.dcc.model.item.weapon.Staff
import cl.uchile.dcc.model.item.weapon.Wand

import scala.collection.mutable

/**
 * A black wizard, a magic character that casts black magic spells and can
 * equip daggers, wands and staffs.
 *
 * @param name the name of the character.
 * @param maxHealthPoints the maximum health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 * @param mana the maximum mana points of the character.
 */
class BlackWizard(name: String, maxHealthPoints: Int, defense: Int, weight: Int, mana: Int)
  extends AbstractMagicCharacter(name, maxHealthPoints, defense, weight, mana):

  /** A black wizard can do every action of a character, plus Thunder and Meteor. */
  override def actions: List[Action] =
    val all = mutable.ListBuffer[Action]()
    for action <- super.actions do all += action
    all += new Thunder()
    all += new Meteor()
    all.toList

  override protected def dispatchTo(action: Action, target: Target): Unit =
    action.doByBlackWizard(this, target)

  override def equipDagger(dagger: Dagger): Unit = equip(dagger)

  override def equipWand(wand: Wand): Unit = equip(wand)

  override def equipStaff(staff: Staff): Unit = equip(staff)