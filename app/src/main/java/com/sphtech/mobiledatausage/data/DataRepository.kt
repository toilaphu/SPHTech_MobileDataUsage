package com.sphtech.mobiledatausage.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
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

    fun getMobileDataUsage(limitValue: Int): Observable<MutableList<MobileDataUsage>> =
        dataUsageRetrofitClient.getMobileDataUsage(limitValue).subscribeOn(Schedulers.io())
            .flatMap { dataResponse -> Observable.fromIterable(dataResponse.result?.records) }
            .flatMap { mobileDataUsage ->
                Log.d("DataRepository", "resultResource: $mobileDataUsage")
                mobileDataUsage.let {
                    appDatabase.mobileDataUsageDao().insertMobileDataUsage(it)
                    Observable.just(it)
                }
            }.toList().toObservable()

    fun getMobileDataUsageDB(): LiveData<PagedList<MobileDataUsage>> =
        appDatabase.mobileDataUsageDao().getMobileDataUsage().toLiveData(
            Config(
                pageSize = 30,
                enablePlaceholders = true,
                maxSize = 200
            )
        )

}