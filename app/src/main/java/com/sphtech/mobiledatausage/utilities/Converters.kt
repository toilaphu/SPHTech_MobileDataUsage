package com.sphtech.mobiledatausage.utilities

import androidx.room.TypeConverter
import java.math.BigDecimal

class Converters {
    @TypeConverter
    fun bigDecimalToString(bigDecimal: BigDecimal): String {
        return bigDecimal.toString()
    }

    @TypeConverter
    fun stringToBigDecimal(string: String): BigDecimal {
        return string.toBigDecimal()
    }
}