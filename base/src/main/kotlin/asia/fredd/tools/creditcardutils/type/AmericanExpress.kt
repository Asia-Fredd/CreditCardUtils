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
         * @return
         * - 不符合 -> null
         * - 符合 -> (非空) CardType/AmericanExpress
         */
        fun Valid(src: CharSequence): CardType? = when {
            // 先判斷長度是否大於 15
            src.length < 15 -> null
            // 判斷是否符合 IIN = 34 或 37
            src[0] != '3' -> null
            src[1] != '4' && src[1] != '7' -> null
            // 最後驗證 Luhn 演算法
            else -> src
                .subSequence(0, 15)
                .takeIf(Luhn.Companion::Check)
               ?.run(::AmericanExpress)
        }
    }
}