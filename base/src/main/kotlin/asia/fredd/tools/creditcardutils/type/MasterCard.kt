@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardDateThru
import asia.fredd.tools.creditcardutils.base.CardType
import asia.fredd.tools.creditcardutils.base.CardType.Companion.IIN
import asia.fredd.tools.creditcardutils.base.Luhn

/**
 * MasterCard
 */
class MasterCard private constructor(override val cardNumber: CharSequence) : CardType {

    override var cardDateThru: CardDateThru? = null

    companion object {
        /**
         * 查核卡號 是否符合 MasterCard 信用卡號編碼
         *
         * IIN = 51–55, 2221-2720
         *
         * @return
         * - 不符合 -> null
         * - 符合 -> (非空) CardType/MasterCard
         *
         * @see
         * [1] https://en.wikipedia.org/wiki/Payment_card_number
         * <br>
         * [2] https://github.com/getbouncer/cardscan-android/blob/1.0.5115/cardscan-base/src/main/java/com/getbouncer/cardscan/base/CreditCardUtils.java#L104-L113
         */
        fun Valid(src: CharSequence): CardType? = when {
            // 先判斷長度是否大於 16
            src.length < 16 -> null
            // 判斷是否符合 IIN = 51–55, 2221-2720
            else -> IIN(src[0], src[1], src[2], src[3]).run {
                // 最後驗證 Luhn 演算法
                when (this) {
                    in 5100..5599 -> src.subSequence(0, 16).run(::convert)
                    in 2221..2720 -> src.subSequence(0, 16).run(::convert)
                    else -> null
                }
            }
        }

        private fun convert(src: CharSequence): CardType? =
            src.takeIf(Luhn.Companion::Check)?.run(::MasterCard)
    }
}