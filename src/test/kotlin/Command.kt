package hk.edu.hkbu.comp.comp4107.playground

import kotlin.random.Random

interface Command {
    fun execute(target: General)
}

class Acedia : Command {
    override fun execute(target: General) {
        println("${target.name} judging the Acedia card.")

        // 1/4 chance to dodge (success)
        val success = Random.nextDouble() < 0.25

        if (success) {
            println("${target.name} dodged the Acedia card.")
        } else {
            println("${target.name} can't dodge the Acedia card. Skipping one round of Play Phase.")
            target.skipPlayPhase = true
        }
    }
}