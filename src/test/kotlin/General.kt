package hk.edu.hkbu.comp.comp4107.playground

import kotlin.random.Random

abstract class General(override val name: String) : Player {
    override var identity: String = "Unknown"
    lateinit var strategy: Strategy

    abstract override val maxHP: Int
    override var currentHP: Int = 0
    override var numOfCards: Int = 4

    var skipPlayPhase: Boolean = false
    var pendingCommand: Command? = null

    abstract val force: String

    override fun hasAttackCard(): Boolean {
        return Random.nextDouble() < 0.2 // 20% chance
    }

    override fun playTurn() {
        preparationPhase()
        judgementPhase()
        drawPhase()

        if (!skipPlayPhase) {
            playPhase()
        } else {
            skipPlayPhase = false
        }

        discardPhase()
        finalPhase()
    }

    open fun preparationPhase() {}

    open fun judgementPhase() {
        pendingCommand?.execute(this)
        pendingCommand = null
    }

    open fun drawPhase() {
        println("$name draws 2 cards and now has ${numOfCards + 2} card(s).")
        numOfCards += 2
    }

    open fun playPhase() {
        if (::strategy.isInitialized) {
            val others = GeneralManager.players.filter { it != this }
            strategy.playCard(this, others)
        }
    }

    open fun discardPhase() {
        if (numOfCards > currentHP) {
            val toDiscard = numOfCards - currentHP
            println("$name discards $toDiscard card(s), now has $currentHP card(s).")
            numOfCards = currentHP
        } else {
            println("$name discards 0 card(s), now has $numOfCards card(s).")
        }
    }

    open fun finalPhase() {}

    override fun hasDodgeCard(): Boolean = Random.nextDouble() < 0.15

    override fun dodgeAttack(): Boolean {
        if (hasDodgeCard()) {
            println("$name dodged attack by spending a dodge card.")
            return true
        }
        return false
    }

    override fun beingAttacked() {
        println("$name being attacked.")
        val success = dodgeAttack()

        if (::strategy.isInitialized && strategy is LordStrategy) {
            if (success) {
                (strategy as LordStrategy).notifyObservers("Dodged")
            } else {
                currentHP--
                println("$name can't dodge attack. Current HP is reduced to $currentHP")
                (strategy as LordStrategy).notifyObservers("Hit")
            }
        } else if (!success) {
            currentHP--
            println("$name can't dodge attack. Current HP is reduced to $currentHP")
        }
    }
}


    public class ZhouYu : General("Zhou Yu") {
    override val maxHP = 3
    override val force: String = "Wu"

    override fun drawPhase() {
        println("[Heroism] $name draws 3 cards and now has ${numOfCards + 3} card(s).")
        numOfCards += 3
    }
}

    public class DiaoChan : General("Diao Chan") {
    override val maxHP = 3
    override val force: String = "Qun"

    override fun finalPhase() {
        numOfCards += 1
        println("[Beauty Outshining the Moon] $name now has $numOfCards card(s).")
    }
}


    public class LiuBei : General("LiuBei"){ override val maxHP: Int = 4; override val force: String = "Shu" }
    public class SunQuan : General("SunQuan"){ override val maxHP: Int = 4; override val force: String = "Wu" }
    public class ZhangFei : General("ZhangFei"){ override val maxHP: Int = 4; override val force: String = "Shu" }
    public class ZhaoYun : General("ZhaoYun"){ override val maxHP: Int = 4; override val force: String = "Shu" }