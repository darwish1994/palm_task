package com.theminesec.example.question1.core.di

import com.theminesec.example.question1.feature.chat.data.local.MessageDao
import com.theminesec.example.question1.feature.chat.data.remote.MessageApi
import com.theminesec.example.question1.feature.chat.data.repo.MessageRepositoryImpl
import com.theminesec.example.question1.feature.chat.domain.repo.MessageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class RepoModule {
    @Provides
    fun provideMessageRepo(
         localDataSource: MessageDao,
        remoteDataSource: MessageApi
    ): MessageRepository = MessageRepositoryImpl(localDataSource, remoteDataSource)
}