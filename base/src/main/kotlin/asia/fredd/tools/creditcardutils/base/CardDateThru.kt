@file:Suppress("FunctionName", "SpellCheckingInspection")

package asia.fredd.tools.creditcardutils.base

import java.util.*


class CardDateThru private constructor(val date: CharSequence) {
    companion object {
        fun Valid(src: CharSequence): CardDateThru? = src.takeIf { it.length == 4 }
            ?.run {
                dateThru {
                    month = (src[0] - '0') * 10 + (src[1] - '0')
                    year  = (src[2] - '0') * 10 + (src[3] - '0') + 2000
                }
            }?.takeUnless { ocrDateThru ->
                // 月無效 或 年無效
                ocrDateThru.month !in 1..12 || ocrDateThru.year < 2020
            }?.let { ocrDateThru ->
                /*
                    因為是邊掃描(OCR), 不想使用 DateFormat 去解析比較
                    所以直接比大小
                 */
                val (nowMonth, nowYear) = tempCalendar.run {
                    get(Calendar.MONTH) + 1 to get(Calendar.YEAR)
                }
                when {
                    // 今年之後才過期
                    ocrDateThru.year > nowYear -> CardDateThru(src)
                    // 今年本月之後才過期
                    ocrDateThru.year == nowYear
                            && ocrDateThru.month >= nowMonth -> CardDateThru(src)
                    // 今年之前就過期
                    else -> null
                }
            }

        private var tempCalendar: Calendar = Calendar.getInstance()
            get() = field.takeIf {
                // 30 秒 內 不更新
                System.currentTimeMillis() - it.timeInMillis < 30 * 1000L
            } ?: Calendar.getInstance().apply {
                field = this
            }

        private fun dateThru(block: DateThru.() -> Unit) = DateThru().apply(block)

        private data class DateThru(var month: Int = 12, var year: Int = 19)

    }
}