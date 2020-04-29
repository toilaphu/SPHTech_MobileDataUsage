package com.sphtech.mobiledatausage.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sphtech.mobiledatausage.adapters.DataUsageAdapter
import com.sphtech.mobiledatausage.api.NetworkState
import com.sphtech.mobiledatausage.api.Status
import com.sphtech.mobiledatausage.data.MobileDataUsageByYear
import com.sphtech.mobiledatausage.databinding.FragmentDataUsageBinding
import com.sphtech.mobiledatausage.utilities.sumByBigDecimal
import com.sphtech.mobiledatausage.viewmodels.MobileDataUsageViewModel
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class DataUsageFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var mobileDataUsageViewModel: MobileDataUsageViewModel
    private var isShowLoadingOnSwipe = false
    private var networkState: NetworkState? = null

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentDataUsageBinding.inflate(inflater, container, false)
        context ?: return binding.root

        val adapter = DataUsageAdapter()
        binding.dataUsageRv.adapter = adapter

        mobileDataUsageViewModel =
            ViewModelProvider(this, factory).get(MobileDataUsageViewModel::class.java)
        mobileDataUsageViewModel.requestMobileDataUsageFromServer()
        mobileDataUsageViewModel.getMobileDataUsageDB().observe(this, Observer { mobileDataUsages ->
            mobileDataUsages?.let { dataUsageList ->
                Log.d("DataUsageFragment", "DataUsageList: $dataUsageList")
                val mobileDataUsageByYears = dataUsageList.groupBy { mobileDataUsage ->
                    mobileDataUsage.quarter.substring(0, 4)
                }
                    .mapValues {
                        it.value.sumByBigDecimal { it.dataVolume }
                    }.toList().map { pairValue ->
                        MobileDataUsageByYear(
                            pairValue.first.toInt(),
                            pairValue.second
                        )
                    }.filter { dataUsageByYear -> dataUsageByYear.year in 2008..2018 }
                Log.d("DataUsageFragment", "DataUsageList-AfterGroup: $mobileDataUsageByYears")
                binding.isEmptyData = mobileDataUsageByYears.isEmpty()
                binding.emptyNotice.visibility =
                    if (networkState?.status == Status.FAILED && mobileDataUsageByYears.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(mobileDataUsageByYears)
            }
        })
        mobileDataUsageViewModel.networkState.observe(this, Observer {
            networkState = it
            binding.swipeRefreshView.isRefreshing =
                (it.status == Status.RUNNING && isShowLoadingOnSwipe)
        })
        binding.swipeRefreshView.setOnRefreshListener {
            isShowLoadingOnSwipe = true
            mobileDataUsageViewModel.requestMobileDataUsageFromServer()
        }
        return binding.root
    }

}