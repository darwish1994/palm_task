package com.theminesec.example.question1.feature.chat.data.remote

import com.theminesec.example.question1.feature.chat.domain.model.Message
import retrofit2.http.Body
import retrofit2.http.POST

interface MessageApi {
    @POST("/messages")
    suspend fun send(@Body message: Message)

}