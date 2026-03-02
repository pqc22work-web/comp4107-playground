package hk.edu.hkbu.comp.comp4107.playground

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class WeiOnlyNonLordFactory : NonLordFactory() {
    override fun createGeneral(name: String): General? {
        val general = super.createGeneral(name)
        // Check if the general is a WeiGeneral (SimaYi, XuChu, or ZhenJi)
        if (general != null && general !is WeiGeneral) {
            println("${general.name} is discarded as he/she is not a Wei.")
            return createGeneral("SimaYi") // Force a Wei general for the test
        }
        return general
    }
}

class WeiTest {
    @Test
    fun testEntourage() {
        // Use the singleton GeneralManager
        val caoCao = CaoCao()
        GeneralManager.addPlayer(caoCao)
        println("General Cao Cao created.")

        val fakeFactory = WeiOnlyNonLordFactory()
        val names = listOf("SimaYi", "XuChu", "ZhenJi")

        var lastGeneral: WeiGeneral = caoCao
        for (name in names) {
            val weiGeneral = fakeFactory.createGeneral(name) as WeiGeneral
            GeneralManager.addPlayer(weiGeneral)
            // Link them for the entourage chain logic
            lastGeneral.nextHandler = weiGeneral
            lastGeneral = weiGeneral
        }

        // Test the entourage logic
        val result = caoCao.entourage()
        assertTrue(result, "Cao Cao's entourage should help him dodge the attack.")
    }
}