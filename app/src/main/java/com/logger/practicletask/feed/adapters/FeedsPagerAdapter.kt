package com.logger.practicletask.feed.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.logger.practicletask.feed.all.AllFeedsFragment
import com.logger.practicletask.feed.chicago.ChicagoFeedsFragment
import com.logger.practicletask.feed.losangeles.LosAngelesFeedsFragment
import com.logger.practicletask.feed.newyork.NewYorkFeedsFragment

const val ALL_PAGE_INDEX = 0
const val CHICAGO_PAGE_INDEX = 1
const val NEW_YORK_PAGE_INDEX = 2
const val LOS_ANGELES_PAGE_INDEX = 3

class FeedsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    private val tabFragmentsCreators: Map<Int, () -> Fragment> = mapOf(
        ALL_PAGE_INDEX to { AllFeedsFragment() },
        CHICAGO_PAGE_INDEX to { ChicagoFeedsFragment() },
        NEW_YORK_PAGE_INDEX to { NewYorkFeedsFragment() },
        LOS_ANGELES_PAGE_INDEX to { LosAngelesFeedsFragment() }
    )

    override fun getItemCount() = tabFragmentsCreators.size

    override fun createFragment(position: Int): Fragment {
        return tabFragmentsCreators[position]?.invoke() ?: throw IndexOutOfBoundsException()
    }
}