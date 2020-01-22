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
            assertEquals(isValid, actual)
            println("${number.toString().padEnd(16)} is ${if (actual) "valid" else "invalid"}")
        }
    }

    @Test
    fun Guess() {
        Array(10) { index ->
            "37598754321001$index"
        }.forEach { cardNumber ->
            val isValid = Luhn.Check(cardNumber)
            if (isValid) {
                println("$cardNumber = $isValid")
            } else {
                System.err.println("$cardNumber = $isValid")
            }
        }
        println()
        val combines = arrayOf(
            intArrayOf(1, 7),
            intArrayOf(1, 7),
            intArrayOf(1, 7),
            intArrayOf(1, 7)
        )
        combines[0].forEach { n0 ->
            combines[1].forEach { n1 ->
                combines[2].forEach { n2 ->
                    combines[3].forEach { n3 ->
                        val cardNumber = "3${n0}598${n1}5432${n2}00${n3}2"
                        val isValid = Luhn.Check(cardNumber)
                        if (isValid) {
                            println("$cardNumber = $isValid")
                        } else {
                            System.err.println("$cardNumber = $isValid")
                        }
                    }
                }
            }
        }
    }
}
