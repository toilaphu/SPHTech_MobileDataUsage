package com.sphtech.mobiledatausage.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sphtech.mobiledatausage.api.NetworkState
import com.sphtech.mobiledatausage.data.DataRepository
import com.sphtech.mobiledatausage.data.MobileDataUsage
import com.sphtech.mobiledatausage.data.MobileDataUsageByYear
import io.reactivex.android.schedulers.AndroidSchedulers
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

class MobileDataUsageViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    companion object {
        private const val ITEM_LIMIT = 100
    }

    val networkState = MutableLiveData<NetworkState>()

    @SuppressLint("CheckResult")
    fun requestMobileDataUsageFromServer() {
        networkState.value = NetworkState.LOADING
        dataRepository.getMobileDataUsage(ITEM_LIMIT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    networkState.value = NetworkState.LOADED
                },
                { errorThrowable ->
                    Log.d("DataUsageViewModel", "errorThrowable: ${errorThrowable.message}")
                    networkState.value = NetworkState.error(errorThrowable.message)
                }
            )
    }

    fun getMobileDataUsageDB(fromYear: Int, toYear: Int): LiveData<List<MobileDataUsageByYear>> {
        return Transformations.map(dataRepository.getMobileDataUsageDB()) { dataUsageList ->
            return@map mapDataUsageQuarterToYear(dataUsageList, fromYear, toYear)
        }
    }

    fun mapDataUsageQuarterToYear(
        mobileDataUsageList: List<MobileDataUsage>,
        fromYear: Int,
        toYear: Int
    ): List<MobileDataUsageByYear> {
        val quarterDecrease = ArrayList<String>()
        return mobileDataUsageList.groupBy { mobileDataUsage ->
            mobileDataUsage.quarter.substring(0, 4)
        }
            .mapValues {
                var volumeTotal = BigDecimal.ZERO
                it.value.iterator().let { valueIterator ->
                    var current = it.value[0]
                    while (valueIterator.hasNext()) {
                        val nextValue = valueIterator.next()
                        volumeTotal += nextValue.dataVolume
                        if (current.dataVolume > nextValue.dataVolume) {
                            quarterDecrease.add(it.key)
                        }
                        current = nextValue
                    }
                }
                return@mapValues volumeTotal
            }.toList().map { pairValue ->
                MobileDataUsageByYear(
                    pairValue.first.toInt(),
                    pairValue.second,
                    quarterDecrease.indexOf(pairValue.first) >= 0
                )
            }.filter { dataUsageByYear -> dataUsageByYear.year in fromYear..toYear }
    }

}