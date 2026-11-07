package cl.uchile.dcc
package model.unit.character

/**
 * A character that can cast spells. Besides the attributes of every
 * character, it has mana points.
 */
trait MagicCharacter extends GameCharacter:

  /** Returns the maximum mana points of this character. */
  def maxMana: Int

  /** Returns the current mana points of this character. */
  def mana: Int

  /**
   * Spends the mana a spell costs and returns the magic damage of the spell:
   * the magic attack of the equipped weapon, increased if a magic strength
   * potion was consumed before.
   *
   * @param manaCost the mana points the spell costs.
   * @return the magic damage of the spell.
   * @throws InvalidActionException if this character does not have enough mana
   *                                 or has no magic weapon equipped.
   */
  def castSpell(manaCost: Int): Int