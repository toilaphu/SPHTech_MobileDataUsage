package com.sphtech.mobiledatausage.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sphtech.mobiledatausage.utilities.Converters

@Database(entities = [MobileDataUsage::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mobileDataUsageDao(): MobileDataUsageDao
}