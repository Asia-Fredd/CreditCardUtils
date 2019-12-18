@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import asia.fredd.tools.creditcardutils.base.CardType.Companion.IIN
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
            else -> IIN(src[0], src[1], src[2], src[3], src[4], src[5]).run {
                // 最後驗證 Luhn 演算法
                when (this) {
                    in 601100..601199,
                    in 622126..622925,
                    in 624000..626999,
                    in 628200..628899,
                    in 640000..659999 -> src.subSequence(0, 16).run(::convert)  // 最後驗證 Luhn 演算法; TODO: 未處理卡號長度大於16~19
                    else -> null
                }
            }
        }

        private fun convert(src: CharSequence): CardType? =
            src.takeIf(Luhn.Companion::Check)?.run(::DiscoverCard)
    }
}