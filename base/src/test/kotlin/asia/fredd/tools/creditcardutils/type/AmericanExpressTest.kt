package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import kotlin.test.Test
import kotlin.test.assertEquals

class AmericanExpressTest {
    @Test
    fun test() {
        val card = AmericanExpress.Valid("345802604558307"+"123456789")
        assert(card != null)
        assert(card is CardType)
        assert(card is AmericanExpress)
        assertEquals("345802604558307", card?.cardNumber)
    }
}