package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import kotlin.test.Test
import kotlin.test.assertEquals

class MasterCardTest {
    @Test
    fun test() {
        MasterCard.Valid("5450089133971306" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is MasterCard)
            assertEquals("5450089133971306", card?.cardNumber)
        }
        MasterCard.Valid("5213963360818570" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is MasterCard)
            assertEquals("5213963360818570", card?.cardNumber)
        }
        MasterCard.Valid("2223129969730708" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is MasterCard)
            assertEquals("2223129969730708", card?.cardNumber)
        }
    }
}