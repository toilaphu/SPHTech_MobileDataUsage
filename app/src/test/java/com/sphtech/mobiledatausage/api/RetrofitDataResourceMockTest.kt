package com.sphtech.mobiledatausage.api

import com.sphtech.mobiledatausage.DataResponseDummy
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit

class RetrofitDataResourceMockTest {
    private var mockWebServer = MockWebServer()
    private lateinit var dataUsageRetrofitClient: DataUsageRetrofitClient

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
    }

    @After
    fun destroyALL() {
        mockWebServer.shutdown()
    }

    @Test
    fun testDataUsageResponse() {
        mockWebServer.enqueue(DataResponseDummy.getDataUsageResponse())
       val dataResponse: ResponseModel.DataStoreResponse = dataUsageRetrofitClient.getMobileDataUsage(100).blockingFirst()
        Assert.assertTrue(dataResponse.success)
        Assert.assertEquals(dataResponse.result?.limit, dataResponse.result?.records?.size)
    }

    @Test
    fun testDataUsageEmptyResponse() {
        mockWebServer.enqueue(DataResponseDummy.getDataUsageEmptyRecordsResponse())
        val dataResponse: ResponseModel.DataStoreResponse = dataUsageRetrofitClient.getMobileDataUsage(100).blockingFirst()
        Assert.assertTrue(dataResponse.success)
        dataResponse.result?.records?.isEmpty()?.let { Assert.assertTrue(it) }
    }

    @Test
    fun testDataUsageErrorResponse() {
        var dataResponse: ResponseModel.DataStoreResponse? = null
        mockWebServer.enqueue(DataResponseDummy.getDataUsageBadRequestResponse())
        try {
            dataResponse = dataUsageRetrofitClient.getMobileDataUsage(100).blockingFirst()
        }catch (e: Exception) {
        }
        Assert.assertNull(dataResponse)
    }

}