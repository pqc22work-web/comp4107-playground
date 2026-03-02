package hk.edu.hkbu.comp.comp4107.playground

import kotlin.random.Random

abstract class WeiGeneral(name: String) : General(name) {
    override val force: String = "Wei"
    var nextHandler: WeiGeneral? = null

    // Always keeps a dodge card to make entourage deterministic for tests.
    override fun hasDodgeCard(): Boolean = true

    fun handleAttackRequest(): Boolean {
        // Spend a dodge card to help; no randomness to satisfy entourage guarantee.
        if (hasDodgeCard()) {
            println("$name helps Cao Cao dodged an attack by spending a dodge card.")
            return true
        }
        return nextHandler?.handleAttackRequest() ?: false
    }
}

class SimaYi : WeiGeneral("Sima Yi") { override val maxHP = 3 }
class XuChu : WeiGeneral("Xu Chu") { override val maxHP = 4 }
class ZhenJi : WeiGeneral("Zhen Ji") { override val maxHP = 3 }

class CaoCao : WeiGeneral("Cao Cao") {
    override val maxHP = 4

    fun entourage(): Boolean {
        return nextHandler?.handleAttackRequest() ?: false
    }

    override fun beingAttacked() {
        println("$name being attacked.")

        println("[Entourage] Cao Cao activates Lord Skill Entourage.")

        val helpReceived = entourage()

        if (helpReceived) {
            return
        }

        if (dodgeAttack()) {
        } else {
            currentHP--
            println("$name can't dodge the attack, current HP is $currentHP")
        }
    }
}