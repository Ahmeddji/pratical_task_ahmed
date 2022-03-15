package com.logger.practicletask.network

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface RemoteApiService {

    companion object {
        const val BASE_URL = "https://api.npoint.io/"
    }

    @GET("368124fffd7ae70f3b57")
    suspend fun getAllFeeds(): Response<ResponseBody>
}