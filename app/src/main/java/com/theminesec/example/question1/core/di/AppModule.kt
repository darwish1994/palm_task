package com.theminesec.example.question1.core.di

import android.content.Context
import androidx.room.Room
import com.theminesec.example.question1.core.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext applicationContext: Context): AppDataBase {
        return Room.databaseBuilder(
            applicationContext,
            AppDataBase::class.java,
            "message-database"
        ).build()
    }

    @Provides
    fun provideMessageDao(appDataBase: AppDataBase) = appDataBase.messageDao()



}