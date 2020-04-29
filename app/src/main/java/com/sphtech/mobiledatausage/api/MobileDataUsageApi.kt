package com.sphtech.mobiledatausage.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * API communication setup
 */
interface MobileDataUsageApi {

    @GET("action/datastore_search")
    fun getDataStore(@Query("resource_id") resourceId: String, @Query("limit") limit: Int): Observable<ResponseModel.DataStoreResponse>

}