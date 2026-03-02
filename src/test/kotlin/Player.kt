package hk.edu.hkbu.comp.comp4107.playground

interface Player {
    val name: String
    val maxHP: Int
    var currentHP: Int
    var numOfCards: Int
    var identity: String
    fun beingAttacked()
    fun dodgeAttack(): Boolean
    fun hasDodgeCard(): Boolean

    fun hasAttackCard(): Boolean

    fun playTurn()
}