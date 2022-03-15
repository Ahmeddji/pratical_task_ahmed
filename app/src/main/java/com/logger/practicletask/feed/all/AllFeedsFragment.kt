package com.logger.practicletask.feed.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.logger.practicletask.binding.autoCleared
import com.logger.practicletask.databinding.FragmentAllFeedsBinding
import com.logger.practicletask.feed.FeedsViewModel
import com.logger.practicletask.feed.adapters.FeedsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllFeedsFragment: Fragment() {

    private var binding: FragmentAllFeedsBinding by autoCleared()
    private val viewModel: FeedsViewModel by viewModels(ownerProducer = { requireParentFragment() })
    private var adapter: FeedsAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllFeedsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAllFeedsRecyclerView()
        registerObservers()
        registerEvents()
    }

    private fun setupAllFeedsRecyclerView() {
        adapter = FeedsAdapter() { position, item ->
            item.isSelected = !(item.isSelected ?: false)
            adapter?.notifyItemChanged(position)
        }

        binding.recyclerViewAllFeeds.adapter = adapter

        viewModel.allFeedsLiveData.observe(viewLifecycleOwner) {
            adapter?.submitList(it)
        }
    }

    private fun registerEvents() {
    }

    private fun refreshListener() {

    }

    private fun registerObservers() {

    }
}