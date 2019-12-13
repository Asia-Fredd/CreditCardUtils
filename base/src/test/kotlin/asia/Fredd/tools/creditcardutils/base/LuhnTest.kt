@file:Suppress("TestFunctionName")

package asia.fredd.tools.creditcardutils.base

import kotlin.test.Test
import kotlin.test.assertEquals

class LuhnTest {

    @Test
    fun Check() {
        val samples = arrayOf<Pair<Boolean, CharSequence>>(
            true  to "49927398716",
            false to "49927398717",
            false to "1234567812345678",
            true  to "1234567812345670",
            true  to "1190013011900009",
            true  to StringBuilder(),
            false to StringBuilder().append('1')
        )
        for ((isValid, number) in samples) {
            val actual = Luhn.Check(number)
            assertEquals(actual, isValid)
            println("${number.toString().padEnd(16)} is ${if (actual) "valid" else "invalid"}")
        }
    }
}
