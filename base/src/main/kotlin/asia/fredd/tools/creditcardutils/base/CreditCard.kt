@file:Suppress("FunctionName", "SpellCheckingInspection")

package asia.fredd.tools.creditcardutils.base

import asia.fredd.tools.creditcardutils.type.*
import java.lang.StringBuilder
import java.util.regex.Pattern

class CreditCard {

    /**
     * 靜態類
     */
    companion object {

        @JvmStatic
        fun ExtractCardDateThru(src: CharSequence): CardDateThru? =
            ExtractNumber(src, DatePattern)?.run(CardDateThru.Companion::Valid)

        @JvmStatic
        fun ExtractCardNumber(src: CharSequence): CardType? = ExtractNumber(src)?.run{
                  let(AmericanExpress.Companion::Valid)
                ?:let(DiscoverCard.Companion::Valid)
                ?:let(VisaCard.Companion::Valid)
                ?:let(MasterCard.Companion::Valid)
                ?:let(UnionPay.Companion::Valid)
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

        /**
         * 信用卡 日期 樣板
         */
        private val DatePattern: Pattern = Pattern.compile(
            //"(01|02|03|04|05|06|07|08|09|10|11|12)\\/([2-9])(\\d)"
            "(0|1)(\\d)\\/([2-9])(\\d)"
        )
    }
}