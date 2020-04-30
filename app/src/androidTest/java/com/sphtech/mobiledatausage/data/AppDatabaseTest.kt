package com.sphtech.mobiledatausage.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.sphtech.mobiledatausage.utilities.getValue
import org.hamcrest.Matchers
import org.junit.*
import org.junit.runner.RunWith
import java.math.BigDecimal


@RunWith(AndroidJUnit4::class)
class AppDatabaseTest {

    private lateinit var mobileDataUsageDao: MobileDataUsageDao
    private lateinit var appDatabase: AppDatabase
    private val mobileData2009q1 =  MobileDataUsage(1, "2009-Q1", BigDecimal.valueOf(1.4))

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        mobileDataUsageDao = appDatabase.mobileDataUsageDao()
        mobileDataUsageDao.insertMobileDataUsage(mobileData2009q1)
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

    @Test
    fun testInsertAObject() {
        val mobileDataUsageList = getValue(mobileDataUsageDao.getMobileDataUsage())
        Assert.assertThat(mobileDataUsageList.size, Matchers.equalTo(1))
    }

    @Test
    fun testInsertDuplicatedObject() {
        // mobileData_2009_Q1 inserted already on createDb() function
        mobileDataUsageDao.insertMobileDataUsage(mobileData2009q1)
        val mobileDataUsageList = getValue(mobileDataUsageDao.getMobileDataUsage())
        Assert.assertThat(mobileDataUsageList.size, Matchers.equalTo(1))
    }

}