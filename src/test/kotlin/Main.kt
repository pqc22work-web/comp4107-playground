package hk.edu.hkbu.comp.comp4107.playground

fun main() {
    NonLordFactory.setParticipants(4)

    val lord = LordFactory.createGeneral("LiuBei")
    lord.currentHP = 1
    println("General ${lord.name} created.\n${lord.name}, a lord, has ${lord.currentHP} health point(s).")
    GeneralManager.addPlayer(lord)

    val names = listOf("ZhenJi", "ZhouYu", "SimaYi")
    for (name in names) {
        val general = NonLordFactory.createGeneral(name)
        if (general != null) {
            GeneralManager.addPlayer(general)
        }
    }

    val lordGeneral = GeneralManager.players.find { it.identity == "Lord" } as? General
    val lordStrategy = lordGeneral?.strategy as? LordStrategy

    if (lordStrategy != null) {
        for (player in GeneralManager.players) {
            if (player.identity == "Spy") {
                val spyStrategy = (player as? General)?.strategy as? SpyStrategy
                if (spyStrategy != null) {
                    lordStrategy.attach(spyStrategy)
                }
            }
        }
    }

    println("\n--- Game Start ---")
    GeneralManager.gameStart()
}