package hk.edu.hkbu.comp.comp4107.playground

import kotlin.random.Random

class EightTrigrams(private val player: Player) : Equipment(player) {

    override fun dodgeAttack(): Boolean {
        println("Triggering the Eight Trigrams")
        val success = Random.nextBoolean()

        println("Judgement is $success")

        if (success) {
            println("${player.name} dodged the attack with the eight trigrams.")
            return true
        } else {
            return player.dodgeAttack()
        }
    }

    override fun beingAttacked() {
        println("${player.name} is being attacked.")
        if (dodgeAttack()) {

        } else {
            player.currentHP--
            println("${player.name} can't dodge the attack, current HP is ${player.currentHP}")
        }
    }
}