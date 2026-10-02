package cl.uchile.dcc
package model.item.potion

import cl.uchile.dcc.model.item.Item

/**
 * A potion that a character can consume to get an effect.
 *
 * For now a potion only has a name. Consuming potions and their effects
 * belong to a later delivery.
 */
trait Potion extends Item