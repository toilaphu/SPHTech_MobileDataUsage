package com.sphtech.mobiledatausage.utilities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.math.BigDecimal


class ConvertersTest {

    @Test
    fun bigDecimalToString() {
        assertEquals("-1", Converters().bigDecimalToString(BigDecimal.valueOf(-1)))
        assertEquals("12.1", Converters().bigDecimalToString(BigDecimal.valueOf(12.10)))
        assertEquals("1222333444", Converters().bigDecimalToString(BigDecimal.valueOf(1222333444)))
        assertEquals(
            "1.333339722",
            Converters().bigDecimalToString(BigDecimal.valueOf(1.333339722))
        )
        assertNotEquals(-1, Converters().bigDecimalToString(BigDecimal.valueOf(-1)))
    }

    @Test
    fun stringToBigDecimal() {
        assertEquals(BigDecimal.valueOf(-1), Converters().stringToBigDecimal("-1"))
        assertEquals(BigDecimal.valueOf(2043), Converters().stringToBigDecimal("20,43"))
        assertEquals(BigDecimal.valueOf(1.00043), Converters().stringToBigDecimal("1.000,43"))

        assertEquals(
            BigDecimal.valueOf(1.333339722),
            Converters().stringToBigDecimal("1.333339722")
        )
        assertEquals(BigDecimal.valueOf(1222333444), Converters().stringToBigDecimal("1222333444"))
        assertNotEquals(-1, Converters().stringToBigDecimal("-1"))
        assertNotEquals(1f, Converters().stringToBigDecimal("1"))
    }

}