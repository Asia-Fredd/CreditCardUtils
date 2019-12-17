@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.base

import java.lang.StringBuilder
import java.util.regex.Pattern

sealed class CreditCard(val ccn: CharSequence) {
    /**
     * 美國運通
     */
    class AmericanExpress(ccn: CharSequence) : CreditCard(ccn)

    /**
     * 發現卡
     */
    class DiscoverCard(ccn: CharSequence) : CreditCard(ccn)

    /**
     * MasterCard
     */
    class Mastercard(ccn: CharSequence) : CreditCard(ccn)

    /**
     * Visa Card
     */
    class VisaCad(ccn: CharSequence) : CreditCard(ccn)

    /**
     * 中國銀聯
     */
    class UnionPay(ccn: CharSequence) : CreditCard(ccn)

    /**
     * 靜態類
     */
    companion object {

        @JvmStatic
        fun Extract(src: CharSequence): CreditCard? {
            return null
        }

        @JvmStatic
        internal fun ExtractNumber(
            src: CharSequence,
            pattern: Pattern = DefaultPattern
        ): CharSequence? =
            pattern.matcher(src).takeIf { it.find() }?.run {
                IntArray(groupCount()) { i -> start(i + 1) }.fold(StringBuilder()) { sb, start ->
                    start.takeUnless { it < 0 }
                        ?.run(src::get)
                        ?.run(sb::append)
                        ?: sb
                }
            }

        /**
         * 考慮目前卡號長度 15~19
         *
         * 先萃取出 15~19碼
         */
        private val DefaultPattern: Pattern = StringBuilder("([1-9])")
            .append("[\\-\\r\\n\\s]{0,2}(\\d)".repeat(14))
            .append("[\\-\\r\\n\\s]{0,2}(\\d)").append('?')
            .append("[\\-\\r\\n\\s]{0,2}(\\d)").append('?')
            .append("[\\-\\r\\n\\s]{0,2}(\\d)").append('?')
            .append("[\\-\\r\\n\\s]{0,2}(\\d)").append('?')
            .toString()
            .run(Pattern::compile)

        private fun isAmericanExpress(numbers: CharSequence): AmericanExpress {
            return AmericanExpress("")
        }
    }
}