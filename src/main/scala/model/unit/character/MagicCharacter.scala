package cl.uchile.dcc
package model.unit.character

/**
 * A character that can cast spells. Besides the attributes of every
 * character, it has mana points.
 */
trait MagicCharacter extends GameCharacter:

  /** Returns the mana points of this character. */
  def mana: Int