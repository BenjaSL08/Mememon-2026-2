package cl.uchile.dcc
package model.unit

import cl.uchile.dcc.model.action.Action
import cl.uchile.dcc.model.action.Attack
import cl.uchile.dcc.model.action.Move
import cl.uchile.dcc.model.action.Target
import cl.uchile.dcc.model.unit.character.WhiteWizard

/**
 * An enemy unit.
 *
 * Enemies are different to characters: an enemy has its own attack points and
 * cannot equip weapons or carry items. It can only attack and move.
 *
 * @param name name of the enemy.
 * @param maxHealthPoints maximum health points of the enemy.
 * @param defense defense points of the enemy.
 * @param weight weight of the enemy.
 * @param _attack attack points of the enemy.
 */
class Enemy(name: String, maxHealthPoints: Int, defense: Int, weight: Int, private val _attack: Int)
  extends AbstractGameUnit(name, maxHealthPoints, defense, weight):

  /** Returns the attack points of this enemy. */
  def attack: Int = _attack

  /** An enemy attacks with its own attack points. */
  override def attackDamage: Int = attack

  /** The action bar of an enemy is its weight. */
  override def maxActionBar: Double = weight

  /** An enemy can attack and move. */
  override def actions: List[Action] = List(new Attack(), new Move())

  override protected def dispatchTo(action: Action, target: Target): Unit =
    action.doByEnemy(this, target)

  /**
   * An enemy can be cursed by Purification: it loses 15% of its current
   * health points plus half the magic damage of the caster.
   */
  override def receivePurification(caster: WhiteWizard): Unit =
    checkAlive()
    val magicDamage = caster.castSpell(25)
    receiveDamage(healthPoints * 15 / 100 + magicDamage / 2)

  /**
   * Compares this enemy with another object.
   *
   * @param obj the object to compare with.
   * @return `true` if both enemies are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    super.equals(obj) && attack == obj.asInstanceOf[Enemy].attack
