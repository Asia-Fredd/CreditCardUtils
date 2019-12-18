@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import asia.fredd.tools.creditcardutils.base.Luhn

/**
 * 發現卡
 */
class DiscoverCard private constructor(override val cardNumber: CharSequence) : CardType {
    companion object {
        /**
         * 查核卡號 是否符合 發現卡 信用卡號編碼
         *
         * IIN = 6011, 622126 - 622925, 624000 - 626999, 628200 - 628899, 64, 65
         *
         * @return
         * - 不符合 -> null
         * - 符合 -> (非空) CardType/DiscoverCard
         *
         * @see
         * [1] https://en.wikipedia.org/wiki/Payment_card_number
         * <br>
         * [2] https://github.com/getbouncer/cardscan-android/blob/1.0.5115/cardscan-base/src/main/java/com/getbouncer/cardscan/base/CreditCardUtils.java#L93-L102
         */
        fun Valid(src: CharSequence): CardType? = when {
            // 先判斷長度是否大於 16
            src.length < 16 -> null
            // 判斷是否符合 IIN = 6011, 622126 - 622925, 624000 - 626999, 628200 - 628899, 64, 65
            src[0] != '6' -> null
            else -> when (src[1]) {
                '0', '2', '4', '5' -> src.subSequence(0, 16).run(::convert) // 最後驗證 Luhn 演算法; TODO: 未處理卡號長度大於16~19
                else -> null
            }
        }

        private fun convert(src: CharSequence): CardType? =
            src.takeIf(Luhn.Companion::Check)?.run(::DiscoverCard)
    }
}