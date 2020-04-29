package com.sphtech.mobiledatausage.data

import java.math.BigDecimal

data class MobileDataUsageByYear(var year: Int, var value: BigDecimal) {
    override fun equals(other: Any?) = (other is MobileDataUsageByYear) && year == other.year
    override fun hashCode(): Int {
        var result = year
        result = 31 * result + value.hashCode()
        return result
    }
}