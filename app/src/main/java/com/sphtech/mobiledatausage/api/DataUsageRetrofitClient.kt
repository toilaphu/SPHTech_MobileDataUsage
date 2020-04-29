package com.sphtech.mobiledatausage.api

import com.sphtech.mobiledatausage.utilities.RESOURCE_ID
import io.reactivex.Observable
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataUsageRetrofitClient @Inject constructor(
    private val mobileDataUsageApi: MobileDataUsageApi
) {
    fun getMobileDataUsage(limitValue: Int): Observable<ResponseModel.DataStoreResponse> =
        mobileDataUsageApi.getDataStore(RESOURCE_ID, limitValue)
}