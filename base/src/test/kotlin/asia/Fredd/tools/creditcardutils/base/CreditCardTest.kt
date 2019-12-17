@file:Suppress("TestFunctionName")

package asia.fredd.tools.creditcardutils.base

import org.junit.Test
import java.util.regex.Pattern
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
    fun Extract() {
        val pattern1 = Pattern.compile("(\\d)[-]?(\\d)[-]?(\\d)[-]?(\\d)")
        val matcher1 = pattern1.matcher("12-3-4567")
        matcher1.find().run(::println)
        matcher1.groupCount().run(::println)
        matcher1.group().run(::println)
        matcher1.group(0).run(::println)
        matcher1.group(1).run(::println)
        matcher1.group(2).run(::println)
        matcher1.group(3).run(::println)
        matcher1.group(4).run(::println)
        "------------------------------------------------------------------------------".run(::println)
        matcher1.reset()
        val pattern2 = Pattern.compile("(\\d)(\\d)(\\d)(\\d)")
        val matcher2 = matcher1.usePattern(pattern2)
        matcher2.find().run(::println)
        matcher2.pattern().run(::println)

        matcher1.find().run(::println)
        matcher1.pattern().run(::println)
    }
}