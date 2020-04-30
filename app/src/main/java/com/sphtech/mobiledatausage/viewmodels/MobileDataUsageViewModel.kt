package com.sphtech.mobiledatausage.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.sphtech.mobiledatausage.api.NetworkState
import com.sphtech.mobiledatausage.data.DataRepository
import com.sphtech.mobiledatausage.data.MobileDataUsageByYear
import com.sphtech.mobiledatausage.utilities.sumByBigDecimal
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import javax.inject.Inject

class MobileDataUsageViewModel @Inject constructor(
    private val dataRepository: DataRepository
) : ViewModel() {

    companion object {
        private const val ITEM_LIMIT = 100
    }

    val networkState = MutableLiveData<NetworkState>()
    val yearVolumeDecreaseList = MutableLiveData<List<Int>>()

    @SuppressLint("CheckResult")
    fun requestMobileDataUsageFromServer() {
        networkState.value = NetworkState.LOADING
        dataRepository.getMobileDataUsage(ITEM_LIMIT)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { mutableListMobileDataUsage ->
                    Log.d("DataUsageViewModel", "ResponseData: $mutableListMobileDataUsage")
                    networkState.value = NetworkState.LOADED
                },
                { errorThrowable ->
                    Log.d("DataUsageViewModel", "errorThrowable: ${errorThrowable.message}")
                    networkState.value = NetworkState.error(errorThrowable.message)
                }
            )
    }

    fun getMobileDataUsageDB(fromYear: Int, toYear:Int): LiveData<List<MobileDataUsageByYear>> {
        return Transformations.map(dataRepository.getMobileDataUsageDB()) { dataUsageList ->
            val quarterDecrease = ArrayList<Int>()
            return@map dataUsageList.groupBy { mobileDataUsage ->
                mobileDataUsage.quarter.substring(0, 4)
            }
                .mapValues {
                    it.value.iterator().let { valueIterator ->
                        var current = it.value[0]
                        while (valueIterator.hasNext()) {
                            val nextValue = valueIterator.next()
                            if (current.dataVolume > nextValue.dataVolume) {
                                quarterDecrease.add(it.key.toInt())
                            }
                            current = nextValue
                        }
                        yearVolumeDecreaseList.postValue(quarterDecrease)
                    }
                    return@mapValues it.value.sumByBigDecimal { mobileDataUsage->
                        mobileDataUsage.dataVolume }
                }.toList().map { pairValue ->
                    MobileDataUsageByYear(
                        pairValue.first.toInt(),
                        pairValue.second
                    )
                }.filter { dataUsageByYear -> dataUsageByYear.year in fromYear..toYear }
        }
    }

}