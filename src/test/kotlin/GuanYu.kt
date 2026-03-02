package hk.edu.hkbu.comp.comp4107.playground

class GuanYu {
    val maximumHP = 4
}

class GuanYuAdapter : General("Guan Yu") {
    private val guanYu = GuanYu()

    override val maxHP: Int
        get() = guanYu.maximumHP

    override val force: String = "Shu"

    init {
        currentHP = maxHP
    }
}