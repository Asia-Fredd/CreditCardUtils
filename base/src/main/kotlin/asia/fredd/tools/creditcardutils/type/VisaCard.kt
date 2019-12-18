@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import asia.fredd.tools.creditcardutils.base.Luhn

/**
 * Visa Card
 */
class VisaCard private constructor(override val cardNumber: CharSequence) : CardType {
    companion object {
        /**
         * 查核卡號 是否符合 VISA 信用卡號編碼
         *
         * IIN = 4 (including related/partner brands: Dankort, Electron, etc.)
         *
         * @return
         * - 不符合 -> null
         * - 符合 -> (非空) CardType/VisaCard
         *
         * @see
         * [1] https://en.wikipedia.org/wiki/Payment_card_number
         * <br>
         * [2] https://github.com/getbouncer/cardscan-android/blob/1.0.5115/cardscan-base/src/main/java/com/getbouncer/cardscan/base/CreditCardUtils.java#L115-L117
         */
        fun Valid(src: CharSequence): CardType? = when {
            // 先判斷長度是否大於 16
            src.length < 16 -> null
            // 判斷是否符合 IIN = 4
            src[0] != '4' -> null
            else -> src.subSequence(0, 16).run(::convert) // 最後驗證 Luhn 演算法
        }

        private fun convert(src: CharSequence): CardType? =
            src.takeIf(Luhn.Companion::Check)?.run(::VisaCard)
    }
}