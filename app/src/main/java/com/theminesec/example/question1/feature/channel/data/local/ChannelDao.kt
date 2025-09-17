package com.theminesec.example.question1.feature.channel.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.theminesec.example.question1.feature.channel.data.model.ChannelEntity

@Dao
interface ChannelDao {
    @Query("SELECT * FROM channels WHERE cachedAt > :validTime")
    suspend fun getValidChannels(validTime: Long = System.currentTimeMillis() - 24 * 60 * 60 * 1000): List<ChannelEntity>?
    @Query("SELECT * FROM channels")
    suspend fun getAllChannels(): List<ChannelEntity>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertChannels(channels: List<ChannelEntity>)
}