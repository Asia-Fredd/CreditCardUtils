@file:Suppress("TestFunctionName", "SpellCheckingInspection")

package asia.fredd.tools.creditcardutils.base

import kotlin.test.Test
import kotlin.test.assertEquals

class CardDateThruTest {

    @Test
    fun Valid() {
        // 月無效
        assertEquals("null", CardDateThru.Valid("1322")?.date.toString())
        // 年無效: 假定基準是 2020
        assertEquals("null", CardDateThru.Valid("1219")?.date.toString())
        // 今年之前過期
        assertEquals("null", CardDateThru.Valid("0318")?.date.toString())
        // 今年之後過期
        assertEquals("0320", CardDateThru.Valid("0320")?.date.toString())
        // 今年本月之前過期
        assertEquals("null", CardDateThru.Valid("0319")?.date.toString())
        // 今年本月之後過期
        assertEquals("1219", CardDateThru.Valid("1219")?.date.toString())
    }
}