package com.example.finalplayground.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalplayground.BR
import com.example.finalplayground.R
import com.example.finalplayground.databinding.LayoutDateItemBinding
import com.example.finalplayground.databinding.LayoutLaunchItemBinding
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.ui.common.DateItem
import com.example.finalplayground.ui.common.LaunchItem
import com.example.finalplayground.ui.common.ListItem

class LaunchListRecyclerViewAdapter(
    var data: List<Launch>,
    private var mapOfResults: Map<String?, List<Launch>>,
    private val clickListener: ItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items = mutableListOf<ListItem>()

    init {
        initGroupItems()
    }

    private fun initGroupItems() {
        items.clear()
        mapOfResults.forEach { pair ->
            items.add(DateItem(pair.key))
            pair.value.forEach {
                items.add(LaunchItem(it))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        when (viewType) {
            ListItem.TYPE_DATE_ITEM -> {

                val binding = DataBindingUtil.inflate<LayoutDateItemBinding>(
                    inflater,
                    R.layout.layout_date_item,
                    parent,
                    false
                )
                return DateItemHolder(binding)
            }
            else -> {
                val binding = DataBindingUtil.inflate<LayoutLaunchItemBinding>(
                    inflater,
                    R.layout.layout_launch_item,
                    parent,
                    false
                )
                return LaunchItemHolder(binding, clickListener)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            ListItem.TYPE_DATE_ITEM -> {
                val dateItem = items[position] as DateItem
                (holder as DateItemHolder).bind(dateItem)
            }
            else -> {
                val launchItem = if (items.isEmpty()) {
                    LaunchItem(data[position])
                } else {
                    items[position] as LaunchItem
                }
                (holder as LaunchItemHolder).bind(launchItem)
            }
        }
    }

    override fun getItemCount(): Int {
        return if (items.isEmpty()) data.size else items.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (items.isEmpty()) {
            0
        } else {
            items[position].viewType
        }
    }

    fun setItems(mapOfResults: Map<String?, List<Launch>>, items: List<Launch>) {
        this.data = items
        this.mapOfResults = mapOfResults
        initGroupItems()
        notifyDataSetChanged()
    }

    class DateItemHolder(
        private val binding: LayoutDateItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DateItem) {
            binding.setVariable(BR.item, item.groupHeader)
        }
    }

    class LaunchItemHolder(
        private val binding: LayoutLaunchItemBinding,
        private val clickListener: ItemClickListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LaunchItem) {
            binding.setVariable(BR.item, item.launch)
            itemView.setOnClickListener {
                clickListener.onItemClick(item)
            }
        }
    }
}

interface ItemClickListener {
    fun onItemClick(item: Any?)
}
