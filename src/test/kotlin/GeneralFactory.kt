package hk.edu.hkbu.comp.comp4107.playground

// Singletons for simple factory usage in tests and main.
object LordFactory {
    fun createGeneral(name: String): General {
        val general = when(name) {
            "LiuBei" -> LiuBei()
            "CaoCao" -> CaoCao()
            "SunQuan" -> SunQuan()
            else -> LiuBei()
        }
        general.currentHP = general.maxHP + 1
        general.identity = "Lord"

        if (name == "LiuBei") {
            general.strategy = LiuBeiStrategy()
        } else {
            general.strategy = LordStrategy()
        }

        return general
    }
}

open class NonLordFactory {
    private var availableIdentities = mutableListOf<String>()

    fun setParticipants(count: Int) {
        availableIdentities.clear()
        if (count == 4) {
            availableIdentities.add("Loyalist")
            availableIdentities.add("Rebel")
            availableIdentities.add("Spy")
        }
        else if (count == 5) {
            availableIdentities.add("Loyalist")
            availableIdentities.add("Rebel")
            availableIdentities.add("Rebel")
            availableIdentities.add("Spy")
        }
        availableIdentities.shuffle()
    }

    open fun createGeneral(name: String): General? {
        val general = when(name) {
            "SimaYi" -> SimaYi()
            "XuChu" -> XuChu()
            "ZhenJi" -> ZhenJi()
            "ZhouYu" -> ZhouYu()
            "DiaoChan" -> DiaoChan()
            "GuanYu" -> GuanYuAdapter() // Or regular GuanYu if fixed
            "LvBu" -> object : General("Lv Bu") {
                override val maxHP = 4
                override val force = "Qun"
            }
            "ZhengFei" -> ZhangFei()
            else -> null
        }

        if (general != null) {
            general.currentHP = general.maxHP

            // Assign Identity
            if (availableIdentities.isNotEmpty()) {
                general.identity = availableIdentities.removeAt(0)
            } else {
                general.identity = "Rebel" // Default fallback
            }

            println("General ${general.name} created.\n${general.name}, a ${general.identity.lowercase()}, has ${general.currentHP} health point(s).")

            // Assign Strategy based on Identity
            when (general.identity) {
                "Loyalist" -> general.strategy = LoyalistStrategy()
                "Rebel" -> general.strategy = RebelStrategy()
                "Spy" -> general.strategy = SpyStrategy(general)
            }

        }

        return general
    }

    companion object {
        private val singleton = NonLordFactory()

        fun setParticipants(count: Int) = singleton.setParticipants(count)

        fun createGeneral(name: String): General? = singleton.createGeneral(name)
    }
}
