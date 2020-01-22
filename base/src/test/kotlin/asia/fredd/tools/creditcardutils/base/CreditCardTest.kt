@file:Suppress("TestFunctionName")

package asia.fredd.tools.creditcardutils.base

import asia.fredd.tools.creditcardutils.type.*
import org.junit.Test
import kotlin.test.assertEquals

class CreditCardTest {

    @Test
    fun ExtractNumber() {
        val inpText1: CharSequence = """
            1900-01-01 2007/08/13 1900.01 0.01 1
            90
            0010
            1190
            0-1
            03-22-01 1
            900 13 01 1900 02 31 9 9 9
        """.trimIndent()
        val ccn1 = CreditCard.ExtractNumber(inpText1)
        assertEquals("1190001011900103220", ccn1.toString())
        println(ccn1)
        println(ccn1?.length)
        println("ccn is CharSequence: ${ccn1 is CharSequence}")
        "------------------------------------------------------------------------------".run(::println)
        val inpText2: CharSequence = """
            1900-01-01 2007/08/13 1900.01 0.01 1
            90
            0010
            1190
            0-1
            0/3/22 01 1
            900 13 01 1900 02 31 9 9 9
        """.trimIndent()
        val ccn2 = CreditCard.ExtractNumber(inpText2)
        assertEquals("119000101190010", ccn2.toString())
        println(ccn2)
        println(ccn2?.length)
        println("ccn is CharSequence: ${ccn2 is CharSequence}")
    }

    @Test
    fun ExtractCardNumber() {
        assert(CreditCard.ExtractCardNumber("3458o2b04Ss8307" + "123456789") is AmericanExpress)
        assert(CreditCard.ExtractCardNumber("345802604558307" + "123456789") is AmericanExpress)
        assert(CreditCard.ExtractCardNumber("6011628014586236" + "123456789") is DiscoverCard)
        assert(CreditCard.ExtractCardNumber("4916036765779888" + "123456789") is VisaCard)
        assert(CreditCard.ExtractCardNumber("5450089133971306" + "123456789") is MasterCard)
        assert(CreditCard.ExtractCardNumber("5213963360818570" + "123456789") is MasterCard)
        assert(CreditCard.ExtractCardNumber("2223129969730708" + "123456789") is MasterCard)
        assert(CreditCard.ExtractCardNumber("6291583220040406" + "123456789") is UnionPay)
        assert(CreditCard.ExtractCardNumber("62033623259185366" + "23456789") is UnionPay)
        assert(CreditCard.ExtractCardNumber("8865140391582328" + "123456789") is UnionPay)
        assert(CreditCard.ExtractCardNumber("8875350719104196" + "123456789") is UnionPay)
    }

    @Test
    fun ExtractCardDateThru() {
        // 月無效
        assertEquals("null", CreditCard.ExtractCardDateThru("13/22")?.date.toString())
        // 年無效: 假定基準是 2020
        assertEquals("null", CreditCard.ExtractCardDateThru("12/19")?.date.toString())
        // 今年之前過期(不含今年)
        assertEquals("null", CreditCard.ExtractCardDateThru("03/18")?.date.toString())
        // 今年之後過期(不含今年)
        assertEquals("0320", CreditCard.ExtractCardDateThru("03/20")?.date.toString())
        // 今年本月之前過期(不含本月)
        assertEquals("null", CreditCard.ExtractCardDateThru("03/19")?.date.toString())
        // 今年本月之後過期(不含本月)
        assertEquals("null", CreditCard.ExtractCardDateThru("12/19")?.date.toString())
    }
}