@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.base

import java.lang.StringBuilder
import java.util.regex.Pattern

class CreditCard {
    companion object {
        @JvmStatic
        fun ExtractNumber(src: CharSequence, pattern: Pattern = DefaultPattern): CharSequence? =
            pattern.matcher(src).takeIf { it.find() }?.run {
                IntArray(groupCount()).foldIndexed(StringBuilder()) { index, sb, _ ->
                    start(index + 1).takeUnless { it < 0 }
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
    }
}