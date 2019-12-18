@file:Suppress("FunctionName")

package asia.fredd.tools.creditcardutils.base

interface CardType {

    val cardNumber: CharSequence

    companion object {
        fun IIN(vararg prefix: Char):Int =
            prefix.fold(0) { sum, char -> char - '0' + sum * 10 }
    }
}