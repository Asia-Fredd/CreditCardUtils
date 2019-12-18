@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import asia.fredd.tools.creditcardutils.base.Luhn

/**
 * 美國運通
 */
class AmericanExpress private constructor(override val cardNumber: CharSequence) : CardType {
    companion object {
        /**
         * 查核卡號 是否符合 美國運通 信用卡號編碼
         *
         * IIN = 34, 37
         *
         * @return
         * - 不符合 -> null
         * - 符合 -> (非空) CardType/AmericanExpress
         *
         * @see
         * [1] https://en.wikipedia.org/wiki/Payment_card_number
         * <br>
         * [2] https://github.com/getbouncer/cardscan-android/blob/1.0.5115/cardscan-base/src/main/java/com/getbouncer/cardscan/base/CreditCardUtils.java#L77-L81
         */
        fun Valid(src: CharSequence): CardType? = when {
            // 先判斷長度是否大於 15
            src.length < 15 -> null
            // 判斷是否符合 IIN = 34 或 37
            src[0] != '3' -> null
            else -> when (src[1]) {
                '4', '7' -> src.subSequence(0, 15).run(::convert) // 最後驗證 Luhn 演算法
                else -> null
            }
        }

        private fun convert(src: CharSequence): CardType? =
            src.takeIf(Luhn.Companion::Check)?.run(::AmericanExpress)
    }
}