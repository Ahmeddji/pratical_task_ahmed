package com.logger.practicletask.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.logger.practicletask.models.data.FeedsModelItem
import com.logger.practicletask.repositories.FeedsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedsViewModel @Inject constructor(
    private val feedsRepository: FeedsRepository
): ViewModel() {

    private var _searchText: String? = null
    val searchText: String?
        get() = _searchText

    private val searchEmittingFlow = MutableStateFlow<String?>(null)

    val allFeedsLiveData = feedsRepository.allFeedsLiveData
        .asFlow()
        .map {
            val allList = mutableListOf<FeedsModelItem>()
            it?.let {
                for (feedItem in it) {
                    allList.add(
                        FeedsModelItem(
                            feedItem.city,
                            feedItem.firstName,
                            feedItem.id,
                            feedItem.isSelected,
                            feedItem.lastName
                        )
                    )
                }
            }
            return@map allList
            /*it?.filter {
                it.city.equals(Cities.CHICAGO)
            }*/
        }
        .combine(searchEmittingFlow) { mutableList: MutableList<FeedsModelItem>, search: String? ->
            if (search.isNullOrEmpty())
                return@combine mutableList
            return@combine mutableList.filter {
                (it.firstName?.contains(search, true) == true
                        || it.lastName?.contains(search, true) == true
                        || it.city?.contains(search, true) == true)
            }
        }
        .asLiveData()


    val chicagoLiveData = feedsRepository.allFeedsLiveData
        .asFlow()
        .map {
            val chihcagoList = mutableListOf<FeedsModelItem>()
            it?.let {
                for (feedItem in it) {
                    if (feedItem.city.equals(Cities.CHICAGO)) {
                        chihcagoList.add(
                            FeedsModelItem(
                                feedItem.city,
                                feedItem.firstName,
                                feedItem.id,
                                feedItem.isSelected,
                                feedItem.lastName
                            )
                        )
                    }
                }
            }
            return@map chihcagoList
            /*it?.filter {
                it.city.equals(Cities.CHICAGO)
            }*/
        }
        .combine(searchEmittingFlow) { mutableList: MutableList<FeedsModelItem>, search: String? ->
            if (search.isNullOrEmpty())
                return@combine mutableList
            return@combine mutableList.filter {
                (it.firstName?.contains(search, true) == true
                        || it.lastName?.contains(search, true) == true
                        || it.city?.contains(search, true) == true)
            }
        }
        .asLiveData()

    val newYorkLiveData = feedsRepository.allFeedsLiveData
        .asFlow()
        .map {
            val newYorkList = mutableListOf<FeedsModelItem>()
            it?.let {
                for (feedItem in it) {
                    if (feedItem.city.equals(Cities.NEW_YORK)) {
                        newYorkList.add(
                            FeedsModelItem(
                                feedItem.city,
                                feedItem.firstName,
                                feedItem.id,
                                feedItem.isSelected,
                                feedItem.lastName
                            )
                        )
                    }
                }
            }
            return@map newYorkList
            /*it?.filter {
                it.city.equals(Cities.NEW_YORK)
            }*/
        }
        .combine(searchEmittingFlow) { mutableList: MutableList<FeedsModelItem>, search: String? ->
            if (search.isNullOrEmpty())
                return@combine mutableList
            return@combine mutableList.filter {
                (it.firstName?.contains(search, true) == true
                        || it.lastName?.contains(search, true) == true
                        || it.city?.contains(search, true) == true)
            }
        }
        .asLiveData()

    val losAngelesLiveData = feedsRepository.allFeedsLiveData
        .asFlow()
        .map {
            val losAngelesList = mutableListOf<FeedsModelItem>()
            it?.let {
                for (feedItem in it) {
                    if (feedItem.city.equals(Cities.LOS_ANGELES)) {
                        losAngelesList.add(
                            FeedsModelItem(
                                feedItem.city,
                                feedItem.firstName,
                                feedItem.id,
                                feedItem.isSelected,
                                feedItem.lastName
                            )
                        )
                    }
                }
            }
            return@map losAngelesList
            /*it?.filter {
                it.city.equals(Cities.LOS_ANGELES)
            }*/
        }
        .combine(searchEmittingFlow) { mutableList: MutableList<FeedsModelItem>, search: String? ->
            if (search.isNullOrEmpty())
                return@combine mutableList
            return@combine mutableList.filter {
                (it.firstName?.contains(search, true) == true
                        || it.lastName?.contains(search, true) == true
                        || it.city?.contains(search, true) == true)
            }
        }
        .asLiveData()

    fun loadAllFeeds() {
        viewModelScope.launch {
            feedsRepository.getAllFeeds()
        }
    }

    fun onSearchTextSubmitted(text: String?) {
        if (text?.isNotEmpty() == true) {
            _searchText = text
        } else {
            _searchText = null
        }

        viewModelScope.launch {
            searchEmittingFlow.emit(_searchText)
        }
    }
}

object Cities {
    const val CHICAGO = "Chicago"
    const val NEW_YORK = "NewYork"
    const val LOS_ANGELES = "Los Angeles"
}