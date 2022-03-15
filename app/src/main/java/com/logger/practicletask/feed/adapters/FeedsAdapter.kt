package com.logger.practicletask.feed.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.logger.practicletask.feed.viewholders.FeedsViewHolder
import com.logger.practicletask.models.data.FeedsModelItem

class FeedsAdapter(private val onClick: (position: Int, item: FeedsModelItem) -> Unit)
    : ListAdapter<FeedsModelItem, FeedsViewHolder>(FEEDS_DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedsViewHolder {
        return FeedsViewHolder.create(parent, onClick)
    }

    override fun onBindViewHolder(holder: FeedsViewHolder, position: Int) {
        holder.bindTo(getItem(position))
    }

    companion object {
        private val FEEDS_DIFF_UTIL = object : DiffUtil.ItemCallback<FeedsModelItem>() {
            override fun areItemsTheSame(
                oldItem: FeedsModelItem,
                newItem: FeedsModelItem
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FeedsModelItem,
                newItem: FeedsModelItem
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}