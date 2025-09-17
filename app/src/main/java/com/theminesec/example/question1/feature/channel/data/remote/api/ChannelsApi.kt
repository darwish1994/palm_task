package com.theminesec.example.question1.feature.channel.data.remote.api

import com.theminesec.example.question1.feature.channel.data.remote.response.ChannelsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface ChannelsApi {
    @GET("channels")
    suspend fun getChannels(
        @Query("cursor") cursor: String?,
        @Query("limit") limit: Int?=20
    ): ChannelsResponseDto
}