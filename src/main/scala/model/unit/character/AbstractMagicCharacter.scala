package cl.uchile.dcc
package model.unit.character

import cl.uchile.dcc.model.action.InvalidActionException
import cl.uchile.dcc.model.item.potion.MagicStrengthPotion
import cl.uchile.dcc.model.item.potion.ManaPotion

/**
 * Base implementation shared by every [[MagicCharacter]].
 *
 * A magic character starts with its maximum mana and can consume the magic
 * potions that common characters cannot.
 *
 * @param name the name of the character.
 * @param maxHealthPoints the maximum health points of the character.
 * @param defense the defense points of the character.
 * @param weight the weight of the character.
 * @param _maxMana the maximum mana points of the character.
 */
abstract class AbstractMagicCharacter(name: String, maxHealthPoints: Int, defense: Int, weight: Int, private val _maxMana: Int)
  extends AbstractGameCharacter(name, maxHealthPoints, defense, weight), MagicCharacter:

  /** The current mana points. Private so they only change through spells and potions. */
  private var _mana: Int = _maxMana

  /** Percentage of the magic damage the next spell deals. A magic strength potion raises it to 150. */
  private var _spellPercentage: Int = 100

  override def maxMana: Int = _maxMana

  override def mana: Int = _mana

  /** The weapon is checked first, so nothing changes if the spell cannot be cast. */
  override def castSpell(manaCost: Int): Int =
    val damage = weapon.magicDamage * _spellPercentage / 100
    if manaCost > _mana then
      throw new InvalidActionException(s"$name does not have enough mana")
    _mana -= manaCost
    _spellPercentage = 100
    damage

  /** A magic character can consume a mana potion: it restores 30% of its maximum mana. */
  override def consumeManaPotion(potion: ManaPotion): Unit =
    useUp(potion)
    _mana = Math.min(maxMana, _mana + maxMana * 30 / 100)

  /** A magic character can consume a magic strength potion: its next spell deals 50% more damage. */
  override def consumeMagicStrengthPotion(potion: MagicStrengthPotion): Unit =
    useUp(potion)
    _spellPercentage = 150

  /**
   * Compares this magic character with another object.
   *
   * @param obj the object to compare with.
   * @return `true` if both characters are equal, `false` otherwise.
   */
  override def equals(obj: Any): Boolean =
    super.equals(obj) && maxMana == obj.asInstanceOf[AbstractMagicCharacter].maxMana