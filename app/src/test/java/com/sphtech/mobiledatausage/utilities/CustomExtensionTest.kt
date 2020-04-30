package com.sphtech.mobiledatausage.utilities

import com.sphtech.mobiledatausage.data.MobileDataUsage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test
import java.math.BigDecimal


class CustomExtensionTest {

    @Test
    fun sumByBigDecimal() {
        val mobileDataUsageList: List<MobileDataUsage> = arrayListOf(
            MobileDataUsage(56, "2018-Q1", BigDecimal.valueOf(16.47121)),
            MobileDataUsage(57, "2018-Q2", BigDecimal.valueOf(18.47368)),
            MobileDataUsage(58, "2018-Q3", BigDecimal.valueOf(19.97554729)),
            MobileDataUsage(59, "2018-Q4", BigDecimal.valueOf(20.43921113))
        )
        assertEquals(
            BigDecimal.valueOf(75.35964842),
            mobileDataUsageList.sumByBigDecimal { mobileDataUsage -> mobileDataUsage.dataVolume })
        assertNotEquals(
            BigDecimal.valueOf(75),
            mobileDataUsageList.sumByBigDecimal { mobileDataUsage -> mobileDataUsage.dataVolume })
    }

}