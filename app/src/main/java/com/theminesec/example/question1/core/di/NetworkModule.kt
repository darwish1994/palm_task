package com.theminesec.example.question1.core.di

import com.theminesec.example.question1.feature.channel.data.remote.api.ChannelsApi
import com.theminesec.example.question1.feature.chat.data.remote.MessageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.app.io/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }


    @Provides
    fun provideOKHTTPInstance(): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)



        return httpClient.build()
    }

    @Provides
    fun provideApiService(retrofit: Retrofit): MessageApi = retrofit.create(MessageApi::class.java)

    @Provides
    fun provideChannelsApi(retrofit: Retrofit): ChannelsApi = retrofit.create(ChannelsApi::class.java)


}