package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import kotlin.test.Test
import kotlin.test.assertEquals

class UnionPayTest {
    @Test
    fun test() {
        UnionPay.Valid("6291583220040406" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is UnionPay)
            assertEquals("6291583220040406", card?.cardNumber)
        }
        UnionPay.Valid("6211205630495239" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is UnionPay)
            assertEquals("6211205630495239", card?.cardNumber)
        }
        UnionPay.Valid("62033623259185366" + "23456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is UnionPay)
            assertEquals("6203362325918536" , card?.cardNumber)
            assertEquals("62033623259185366", card?.cardNumber) // TODO: 實際是 17 碼
        }
        UnionPay.Valid("8865140391582328" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is UnionPay)
            assertEquals("8865140391582328", card?.cardNumber)
        }
        UnionPay.Valid("8875350719104196" + "123456789").also { card ->
            assert(card != null)
            assert(card is CardType)
            assert(card is UnionPay)
            assertEquals("8875350719104196", card?.cardNumber)
        }
    }
}