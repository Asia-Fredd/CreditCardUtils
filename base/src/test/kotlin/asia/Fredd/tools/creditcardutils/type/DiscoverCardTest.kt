package asia.fredd.tools.creditcardutils.type

import asia.fredd.tools.creditcardutils.base.CardType
import kotlin.test.Test
import kotlin.test.assertEquals

class DiscoverCardTest {
    @Test
    fun test() {
        val card = DiscoverCard.Valid("6011628014586236"+"123456789")
        assert(card != null)
        assert(card is CardType)
        assert(card is DiscoverCard)
        assertEquals("6011628014586236", card?.cardNumber)
    }
}