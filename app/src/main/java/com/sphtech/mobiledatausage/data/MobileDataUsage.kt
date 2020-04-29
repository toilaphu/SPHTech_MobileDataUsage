package com.sphtech.mobiledatausage.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

@Entity(tableName = "mobile_data_usage")
data class MobileDataUsage(
    @PrimaryKey
    @SerializedName("_id")
    var id: Int,
    @SerializedName("quarter")
    var quarter: String,
    @SerializedName("volume_of_mobile_data")
    var dataVolume: BigDecimal
) {
    override fun equals(other: Any?) = (other is MobileDataUsage) && id == other.id
    override fun hashCode(): Int {
        var result = id
        result = 31 * result + quarter.hashCode()
        result = 31 * result + dataVolume.hashCode()
        return result
    }
}