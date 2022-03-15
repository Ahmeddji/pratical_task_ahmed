package com.logger.practicletask.feed.viewholders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.logger.practicletask.R
import com.logger.practicletask.databinding.ListItemFeedBinding
import com.logger.practicletask.models.data.FeedsModelItem

class FeedsViewHolder(
    private val view: View,
    private val onClick: (position: Int, item: FeedsModelItem) -> Unit
): RecyclerView.ViewHolder(view) {

    private val binding = ListItemFeedBinding.bind(view)
    private var feedsModelItem: FeedsModelItem? = null

    init {
        binding.root.setOnLongClickListener {
            feedsModelItem?.let {
                onClick(adapterPosition, it)
            }
            true
        }
    }

    fun bindTo(item: FeedsModelItem?) {
        feedsModelItem = item
        binding.tvFullName.text = "${item?.firstName} ${item?.lastName}"
        binding.tvCity.text = item?.city
        binding.ivSelection.isVisible = item?.isSelected ?: false
    }

    companion object {
        fun create(
            parent: ViewGroup,
            onClick: (position: Int, item: FeedsModelItem) -> Unit
        ): FeedsViewHolder {
            return FeedsViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_feed, parent, false),
                onClick
            )
        }
    }
}