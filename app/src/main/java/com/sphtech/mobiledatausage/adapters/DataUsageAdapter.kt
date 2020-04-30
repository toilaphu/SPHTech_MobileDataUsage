package com.sphtech.mobiledatausage.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sphtech.mobiledatausage.data.MobileDataUsageByYear
import com.sphtech.mobiledatausage.databinding.ListItemDataUsageBinding
import com.sphtech.mobiledatausage.utilities.AppUtils
import com.sphtech.mobiledatausage.utilities.PAYLOAD_DECREASE_IC


class DataUsageAdapter :
    ListAdapter<MobileDataUsageByYear, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    var yearVolumeDecreaseList = arrayListOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return BasicViewHolder(
            ListItemDataUsageBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let {
            (holder as BasicViewHolder).bind(it, yearVolumeDecreaseList.contains(it.year))
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
            return
        }
        if (payloads.indexOf(PAYLOAD_DECREASE_IC) >= 0) {
            getItem(position)?.let {
                (holder as BasicViewHolder).displayDecreaseIc(yearVolumeDecreaseList.contains(it.year))
            }
        }
    }

    fun updateYearVolumeDecrease(aList: List<Int>) {
        yearVolumeDecreaseList = aList as ArrayList<Int>
        yearVolumeDecreaseList.forEach { volumeDecrease ->
            currentList.map { it.year }.indexOf(volumeDecrease).let {index->
                if (index >= 0) {
                    notifyItemChanged(index, PAYLOAD_DECREASE_IC)
                }
            }
        }
    }

    class BasicViewHolder(
        private val binding: ListItemDataUsageBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.decreaseBtn.setOnClickListener {
                // Prevent a double-click
                AppUtils.disableViewInDuration(it, 1000L)
                // Handle decrease-Btn clicked
            }
        }

        fun bind(item: MobileDataUsageByYear, isVolumeDecrease: Boolean) {
            binding.apply {
                dataUsageByYear = item
                isDecrease = isVolumeDecrease
                executePendingBindings()
            }
        }

        fun displayDecreaseIc(isVolumeDecrease: Boolean) {
            binding.apply {
                isDecrease = isVolumeDecrease
                executePendingBindings()
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MobileDataUsageByYear>() {
            // The ID property identifies when items are the same.
            override fun areItemsTheSame(
                oldItem: MobileDataUsageByYear,
                newItem: MobileDataUsageByYear
            ) = oldItem.year == newItem.year

            // If you use the "==" operator, make sure that the object implements
            // .equals(). Alternatively, write custom data comparison logic here.
            override fun areContentsTheSame(
                oldItem: MobileDataUsageByYear, newItem: MobileDataUsageByYear
            ) = oldItem == newItem
        }
    }

}