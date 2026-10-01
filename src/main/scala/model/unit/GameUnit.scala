package cl.uchile.dcc
package model.unit

/**
 * A unit that takes part in a match.
 *
 * Units are the pieces that a player controls: characters and enemies. Every unit
 * has a name, health points, defense and weight, and can tell whether it is
 * still alive in order to know if the player still has characters to play.
 */
trait GameUnit:

  /** Returns the name of this unit. */
  def name: String

  /** Returns the current health points of this unit. */
  def healthPoints: Int

  /** Returns the defense points of this unit. */
  def defense: Int

  /** Returns the weight of this unit, which determines how fast it gets its turn. */
  def weight: Int

  /** Returns `true` if this unit has more than zero health points. */
  def isAlive: Boolean
