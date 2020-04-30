package com.sphtech.mobiledatausage.viewmodels

import android.content.Context
import android.os.Build
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.sphtech.mobiledatausage.DataResponseDummy.getDataUsageBadRequestResponse
import com.sphtech.mobiledatausage.DataResponseDummy.getDataUsageResponse
import com.sphtech.mobiledatausage.DataResponseDummy.getListMobileDataUsageDummy
import com.sphtech.mobiledatausage.api.*
import com.sphtech.mobiledatausage.data.AppDatabase
import com.sphtech.mobiledatausage.data.DataRepository
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Retrofit
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.P])
class MobileDataUsageVMTest {

    private var mockWebServer = MockWebServer()
    private lateinit var dataUsageRetrofitClient: DataUsageRetrofitClient
    private lateinit var appDatabase: AppDatabase
    private lateinit var dataRepository: DataRepository
    private lateinit var mobileDataUsageViewModel: MobileDataUsageViewModel

    @Before
    fun setup() {
        mockWebServer.start()
        val retrofit = Retrofit.Builder().baseUrl(mockWebServer.url("/"))
        dataUsageRetrofitClient = DataUsageRetrofitClient_Factory.newInstance(
            RetrofitClientBase_ProvideMobileDataUsageServiceFactory.provideMobileDataUsageService(
                RetrofitClientBase(),
                retrofit
            )
        )
        appDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext<Context>(),
            AppDatabase::class.java
        ).build()
        dataRepository = DataRepository(dataUsageRetrofitClient, appDatabase)
        mobileDataUsageViewModel = MobileDataUsageViewModel(dataRepository)
    }

    @After
    fun destroyALL() {
        mockWebServer.shutdown()
        appDatabase.close()
    }

    @Test
    fun testMapDataUsageQuarterToYear() {
        val dataUsageByYearList = mobileDataUsageViewModel.mapDataUsageQuarterToYear(
            getListMobileDataUsageDummy(),
            2010,
            2015
        )
        assertEquals(6, dataUsageByYearList.size)
        assertEquals(BigDecimal.valueOf(7.2), dataUsageByYearList[1].value)
        assertEquals(2013, dataUsageByYearList[3].year)
        assertTrue(dataUsageByYearList[4].isVolumeDecrease)
        assertFalse(dataUsageByYearList[2].isVolumeDecrease)
        assertNotEquals(2, dataUsageByYearList.size)
    }

    @Test
    fun testCallingAPI() {
        mockWebServer.enqueue(getDataUsageResponse())
        mobileDataUsageViewModel.requestMobileDataUsageFromServer()
        assertEquals(NetworkState.LOADING, mobileDataUsageViewModel.networkState.value)
    }

    @Test
    fun testCallAPIBadRequest() {
        mockWebServer.enqueue(getDataUsageBadRequestResponse())
        try {
            dataUsageRetrofitClient.getMobileDataUsage(100).blockingFirst()
        } catch (e: Exception) {
            mobileDataUsageViewModel.networkState.value = NetworkState.error(e.message)
            assertEquals(NetworkState.error(e.message), mobileDataUsageViewModel.networkState.value)
        }
    }

}