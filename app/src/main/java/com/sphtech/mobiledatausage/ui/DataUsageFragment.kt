package com.sphtech.mobiledatausage.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sphtech.mobiledatausage.R
import com.sphtech.mobiledatausage.adapters.DataUsageAdapter
import com.sphtech.mobiledatausage.api.NetworkState
import com.sphtech.mobiledatausage.api.Status
import com.sphtech.mobiledatausage.databinding.FragmentDataUsageBinding
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
        mobileDataUsageViewModel.getMobileDataUsageDB(2008, 2018)
            .observe(this, Observer { mobileDataUsageByYears ->
                binding.isEmptyData = mobileDataUsageByYears.isEmpty()
                binding.emptyNotice.visibility =
                    if (networkState?.status == Status.FAILED && mobileDataUsageByYears.isEmpty()) View.VISIBLE else View.GONE
                adapter.submitList(mobileDataUsageByYears)
            })
        mobileDataUsageViewModel.networkState.observe(this, Observer {
            networkState = it
            if (it.status == Status.FAILED) {
                binding.emptyNotice.visibility =
                    if (adapter.itemCount <= 0) View.VISIBLE else View.GONE
                Toast.makeText(activity, R.string.call_api_error_notice, Toast.LENGTH_SHORT).show()
            }
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