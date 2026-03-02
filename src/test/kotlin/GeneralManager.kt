package hk.edu.hkbu.comp.comp4107.playground

// Singleton so everyone references the same player list.
object GeneralManager {
    val players = mutableListOf<Player>()

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun gameStart() {
        println("Total number of players: ${players.size}")

        if (players.size >= 4) {
            val victim = players.last() as? General
            if (victim != null) {
                println("${victim.name} being placed the Acedia card.")
                victim.pendingCommand = Acedia()
            }
        }


        for (player in players) {
            println("\n--- ${player.name}'s Turn ---")
            player.playTurn()
        }
    }
}