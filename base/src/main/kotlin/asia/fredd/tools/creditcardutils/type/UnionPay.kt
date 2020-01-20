@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardDateThru
import asia.fredd.tools.creditcardutils.base.CardType
import asia.fredd.tools.creditcardutils.base.Luhn

/**
 * 中國銀聯
 */
class UnionPay private constructor(override val cardNumber: CharSequence) : CardType {

    override var cardDateThru: CardDateThru? = null

    companion object {
        /**
         * 查核卡號 是否符合 中國銀聯 信用卡號編碼
         *
         * IIN = 62, 81, 88
         *
         * @return
         * - 不符合 -> null
         * - 符合 -> (非空) CardType/UnionPay
         *
         * @see
         * [1] https://en.wikipedia.org/wiki/Payment_card_number
         * <br>
         * [2] https://github.com/getbouncer/cardscan-android/blob/1.0.5115/cardscan-base/src/main/java/com/getbouncer/cardscan/base/CreditCardUtils.java#L83-L91
         * <br>
         * [3] https://www.creditcardrush.com/china-unionpay-credit-card-generator/
         */
        fun Valid(src: CharSequence): CardType? = when {
            // 先判斷長度是否大於 16
            src.length < 16 -> null
            // 判斷是否符合 IIN = 62, 81, 88; TODO: 銀聯 IIN 好像不只這些
            src[0] == '6' && src[1] == '2' -> src.subSequence(0, 16).run(::convert) // 最後驗證 Luhn 演算法; TODO: 未處理卡號長度大於16~19
            src[0] == '8' && src[1] == '1' -> src.subSequence(0, 16).run(::convert) // 最後驗證 Luhn 演算法; TODO: 未處理卡號長度大於16~19
            src[0] == '8' && src[1] == '8' -> src.subSequence(0, 16).run(::convert) // 最後驗證 Luhn 演算法; TODO: 未處理卡號長度大於16~19
            else -> null
        }

        private fun convert(src: CharSequence): CardType? =
            src.takeIf(Luhn.Companion::Check)?.run(::UnionPay)
    }
}