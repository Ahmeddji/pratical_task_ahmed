package com.logger.practicletask.feed

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.logger.practicletask.R
import com.logger.practicletask.binding.autoCleared
import com.logger.practicletask.databinding.FragmentFeedContainerBinding
import com.logger.practicletask.feed.adapters.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FeedContainerFragment : Fragment() {

    private var binding: FragmentFeedContainerBinding by autoCleared()
    private val viewModel: FeedsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.loadAllFeeds()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFeedContainerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTabs()
        searchListener()
    }

    private fun setupTabs() {
        binding.pagerFeeds.adapter = FeedsPagerAdapter(this)
        TabLayoutMediator(binding.tabLayoutFeeds, binding.pagerFeeds) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            ALL_PAGE_INDEX -> getString(R.string.all)
            CHICAGO_PAGE_INDEX -> getString(R.string.chicago)
            NEW_YORK_PAGE_INDEX -> getString(R.string.new_york)
            LOS_ANGELES_PAGE_INDEX -> getString(R.string.los_angeles)
            else -> null
        }
    }

    private fun searchListener() {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                onSearch()
                true
            } else {
                false
            }
        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                onSearch()
                true
            } else {
                false
            }
        }
    }

    private fun onSearch() {
        viewModel.onSearchTextSubmitted(binding.etSearch.text.trim().toString())
    }
}