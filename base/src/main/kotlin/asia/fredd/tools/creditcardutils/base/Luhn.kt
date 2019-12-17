@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.base

class Luhn {

    companion object {

        @JvmStatic
        fun Check(s: CharSequence): Boolean = isValid(s)

        /**
         * 參考 http://rosettacode.org/wiki/Luhn_test_of_credit_card_numbers#Kotlin
         *
         * 不同的是, param 型態是 CharSequence
         */
        private fun isValid(s: CharSequence): Boolean {
            val t  = s.reversed() /* TODO: 待優化 reversed */
            val s1 = t.filterIndexed { i, _ -> i % 2 == 0 }.sumBy { it - '0' }
            val s2 = t.filterIndexed { i, _ -> i % 2 == 1 }.map { sumDigits((it - '0') * 2) }.sum()
            return (s1 + s2) % 10 == 0
        }
        private fun sumDigits(n: Int) = n / 10 + n % 10
    }

}