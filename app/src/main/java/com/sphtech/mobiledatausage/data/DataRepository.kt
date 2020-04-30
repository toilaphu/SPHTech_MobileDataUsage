package com.sphtech.mobiledatausage.data

import com.sphtech.mobiledatausage.api.DataUsageRetrofitClient
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DataRepository @Inject constructor(
    private val dataUsageRetrofitClient: DataUsageRetrofitClient,
    private val appDatabase: AppDatabase
) {

    fun getMobileDataUsage(limitValue: Int): Observable<MutableList<List<MobileDataUsage>>> =
        dataUsageRetrofitClient.getMobileDataUsage(limitValue).subscribeOn(Schedulers.io())
            .flatMap { dataResponse ->
                Observable.fromArray(dataResponse.result?.records)
            }
            .flatMap { mobileDataUsage ->
                mobileDataUsage.let {
                    appDatabase.mobileDataUsageDao().insertAll(it)
                    Observable.just(it)
                }
            }.toList().toObservable()

    fun getMobileDataUsageDB() = appDatabase.mobileDataUsageDao().getMobileDataUsage()

}