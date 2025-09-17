package com.theminesec.example.question1.feature.channel.data.repo

import com.theminesec.example.question1.feature.channel.data.local.ChannelDao
import com.theminesec.example.question1.feature.channel.data.model.mapToDomain
import com.theminesec.example.question1.feature.channel.data.model.mapToEntity
import com.theminesec.example.question1.feature.channel.data.remote.api.ChannelsApi
import com.theminesec.example.question1.feature.channel.domain.model.Channel
import com.theminesec.example.question1.feature.channel.domain.repo.ChannelsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ChannelsRepositoryImpl @Inject constructor(
    private val api: ChannelsApi,
    private val dao: ChannelDao
) : ChannelsRepository {
    override suspend fun getChannels(cursor: String?): Result<List<Channel>> {
        return withContext(Dispatchers.IO) {
            try {
                val response = api.getChannels(cursor, limit = 20)
                // Cache in Room DB with 24h TTL
                dao.insertChannels(response.data.map { it.mapToEntity() })
                Result.success((dao.getValidChannels() ?: emptyList()).map { it.mapToDomain() })
            } catch (e: Exception) {
                // Fallback to cached data
                Result.success((dao.getValidChannels() ?: emptyList()).map { it.mapToDomain() })
            }
        }
    }

}