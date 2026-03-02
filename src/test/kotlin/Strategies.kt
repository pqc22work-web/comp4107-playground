package hk.edu.hkbu.comp.comp4107.playground

interface Observer {
    fun update(event: String)
}

interface Subject {
    fun attach(observer: Observer)
    fun detach(observer: Observer)
    fun notifyObservers(event: String)
}

abstract class Strategy {
    abstract fun whoToAttack(me: Player, others: List<Player>): Player?

    open fun playCard(me: Player, others: List<Player>) {
        if (me.hasAttackCard()) {
            val target = whoToAttack(me, others)
            if (target != null) {
                println("${me.name} spends a card to attack a ${target.identity.lowercase()}, ${target.name}")
                me.numOfCards--
                target.beingAttacked()
            } else {
                println("${me.name} doesn't have attack card (or no valid target).")
            }
        } else {
            println("${me.name} doesn't have attack card.")
        }
    }
}

open class LordStrategy : Strategy(), Subject {
    private val observers = mutableListOf<Observer>()

    override fun whoToAttack(me: Player, others: List<Player>): Player? {
        return others.find { it.identity == "Rebel" && it.currentHP > 0 }
    }

    override fun attach(observer: Observer) {
        observers.add(observer)
        if (observer is Player) {
            println("${observer.name} is observing lord.")
        }
    }

    override fun detach(observer: Observer) {
        observers.remove(observer)
    }

    override fun notifyObservers(event: String) {
        observers.forEach { it.update(event) }
    }
}

class LoyalistStrategy : Strategy() {
    override fun whoToAttack(me: Player, others: List<Player>): Player? {
        return others.find { it.identity == "Rebel" && it.currentHP > 0 }
    }
}

class RebelStrategy : Strategy() {
    override fun whoToAttack(me: Player, others: List<Player>): Player? {
        return others.find { it.identity == "Lord" && it.currentHP > 0 }
    }
}

class SpyStrategy(val myGeneral: Player) : Strategy(), Observer {
    private var riskLevel = 50.0

    override fun whoToAttack(me: Player, others: List<Player>): Player? {
        return others.find { it.identity == "Rebel" && it.currentHP > 0 }
    }

    override fun update(event: String) {
        if (event == "Dodged") {
            riskLevel *= 0.5
        } else if (event == "Hit") {
            riskLevel *= 1.5
        }
        println("${myGeneral.name} on Lord's Risk Level: ${riskLevel.toInt()}")
    }
}

interface State {
    fun playNextCard(player: Player, others: List<Player>, strategy: Strategy)
}

class HealthyState : State {
    override fun playNextCard(player: Player, others: List<Player>, strategy: Strategy) {
        println("${player.name} is healthy.")
        val lordStrat = strategy as? LordStrategy ?: return
        if (player.hasAttackCard()) {
            val target = lordStrat.whoToAttack(player, others)
            if (target != null) {
                println("${player.name} spends a card to attack a ${target.identity.lowercase()}, ${target.name}")
                player.numOfCards--
                target.beingAttacked()
            } else {
                println("${player.name} doesn't have attack card (or no valid target).")
            }
        } else {
            println("${player.name} doesn't have attack card.")
        }
    }
}

class UnhealthyState : State {
    override fun playNextCard(player: Player, others: List<Player>, strategy: Strategy) {
        println("${player.name} is not healthy.")
        if (player.numOfCards >= 2) {
            player.numOfCards -= 2
            player.currentHP += 1
            println("[Benevolence] ${player.name} gives away two cards and recovers 1 HP, now his HP is ${player.currentHP}.")

            if (player.currentHP > 1) {
                println("${player.name} is now healthy.")
            }
        }
        val lordStrat = strategy as? LordStrategy ?: return
        if (player.hasAttackCard()) {
            val target = lordStrat.whoToAttack(player, others)
            if (target != null) {
                println("${player.name} spends a card to attack a ${target.identity.lowercase()}, ${target.name}")
                player.numOfCards--
                target.beingAttacked()
            } else {
                println("${player.name} doesn't have attack card (or no valid target).")
            }
        } else {
            println("${player.name} doesn't have attack card.")
        }
    }
}

class LiuBeiStrategy : LordStrategy() {
    private var state: State = HealthyState()

    override fun playCard(me: Player, others: List<Player>) {
        if (me.currentHP <= 1) {
            state = UnhealthyState()
        } else {
            state = HealthyState()
        }

        state.playNextCard(me, others, this)
    }
}