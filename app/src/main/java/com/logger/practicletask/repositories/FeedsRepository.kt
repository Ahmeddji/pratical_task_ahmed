package com.logger.practicletask.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.logger.practicletask.models.data.FeedsModelItem
import com.logger.practicletask.network.RemoteApiService
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONArray
import javax.inject.Inject

class FeedsRepository @Inject constructor(private val remoteApiService: RemoteApiService) {

    private val _allFeedsLiveData = MutableLiveData<List<FeedsModelItem>?>()
    val allFeedsLiveData: LiveData<List<FeedsModelItem>?>
        get() = _allFeedsLiveData

    suspend fun getAllFeeds() {
        val response = remoteApiService.getAllFeeds()

        var feedItems = mutableListOf<FeedsModelItem>()

        if (response.isSuccessful && response.body() != null) {
            val jsonArray = JSONArray(response.body()?.string())
            for (i in 0 until jsonArray.length()) {
                val item = jsonArray.getJSONObject(i)
                val feedsModelItem = FeedsModelItem(
                    item.getString("city"),
                    item.getString("first_name"),
                    item.getInt("id"),
                    false,
                    item.getString("last_name")
                )
                feedItems.add(feedsModelItem)
            }
        }

        _allFeedsLiveData.value = feedItems
    }
}