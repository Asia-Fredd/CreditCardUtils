package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import kotlin.test.Test
import kotlin.test.assertEquals

class VisaCardTest {
    @Test
    fun test() {
        val card = VisaCard.Valid("4916036765779888"+"123456789")
        assert(card != null)
        assert(card is CardType)
        assert(card is VisaCard)
        assertEquals("4916036765779888", card?.cardNumber)
    }
}