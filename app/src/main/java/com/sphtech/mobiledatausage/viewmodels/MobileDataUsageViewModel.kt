package com.sphtech.mobiledatausage.viewmodels

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sphtech.mobiledatausage.api.NetworkState
import com.sphtech.mobiledatausage.data.DataRepository
import io.reactivex.android.schedulers.AndroidSchedulers
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

    fun getMobileDataUsageDB() = dataRepository.getMobileDataUsageDB()
}