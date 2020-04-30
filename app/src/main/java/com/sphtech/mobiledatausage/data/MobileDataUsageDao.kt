package com.sphtech.mobiledatausage.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MobileDataUsageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(mobileDataUsages: List<MobileDataUsage>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMobileDataUsage(mobileDataUsage: MobileDataUsage)

    @Query("SELECT * FROM `mobile_data_usage`")
    fun getMobileDataUsage(): LiveData<List<MobileDataUsage>>

}