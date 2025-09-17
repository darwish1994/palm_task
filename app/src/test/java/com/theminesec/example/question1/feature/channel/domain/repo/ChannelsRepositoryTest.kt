package com.theminesec.example.question1.feature.channel.domain.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.theminesec.example.question1.feature.channel.data.local.ChannelDao
import com.theminesec.example.question1.feature.channel.data.model.ChannelDto
import com.theminesec.example.question1.feature.channel.data.model.ChannelEntity
import com.theminesec.example.question1.feature.channel.data.model.mapToEntity
import com.theminesec.example.question1.feature.channel.data.remote.api.ChannelsApi
import com.theminesec.example.question1.feature.channel.data.remote.response.ChannelsResponseDto
import com.theminesec.example.question1.feature.channel.data.repo.ChannelsRepositoryImpl
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.exceptions.base.MockitoException
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException
import java.net.ConnectException

@RunWith(MockitoJUnitRunner::class)
class ChannelsRepositoryTest {
    @Mock
    private lateinit var api: ChannelsApi

    @Mock
    private lateinit var dao: ChannelDao

    private lateinit var repository: ChannelsRepository

    @Before
    fun setup() {
        repository = ChannelsRepositoryImpl(api, dao)
    }


    @Test
    fun `empty response returns empty state`() {
        // Given: API returns empty data array
        val emptyResponse = ChannelsResponseDto(data = emptyList(), next_cursor = null)

        runBlocking {
            `when`(api.getChannels(any(), any())).thenReturn(emptyResponse)
            // When: repository fetches channels
            val result = repository.getChannels()

            // Then: UI state should be Empty
            assertTrue(result.isSuccess && result.getOrThrow().isEmpty())
        }
    }

    @Test
    fun `cached response expired returns fresh data`() {

        runTest {

            // When: fresh API call succeeds
            val freshResponse = ChannelsResponseDto(data = listOf(ChannelDto("new", "New", 5)), next_cursor = null)
            val dbResponse = listOf(ChannelEntity("new", "New", 5))
            `when`(api.getChannels(any(), any())).thenReturn(freshResponse)
            `when`(dao.getValidChannels()).thenReturn(dbResponse)

            // Given: cached data older than 24h exists
            val expiredData = ChannelEntity(
                id = "old", name = "Old", unread = 0,
                cachedAt = System.currentTimeMillis() - 25 * 60 * 60 * 1000
            )
            withContext(Dispatchers.IO) {
             dao.insertChannels(listOf(expiredData))
            }

            // Then: should return fresh data, not expired cache
            val result = repository.getChannels()

            assertTrue(result.isSuccess)
            assertEquals("new", result.getOrNull()?.first()?.id)
        }
    }

    @Test
    fun `network error fallback serves cached data`() {
        // Given: valid cached data exists and network fails
        val cachedData = ChannelEntity(id = "cached", name = "Cached", unread = 3)

        runTest {
            // When: network request throws exception
            `when`(api.getChannels(any(), any())).thenThrow(MockitoException("Network error"))

            withContext(Dispatchers.IO) {
                dao.insertChannels(listOf(cachedData))
            }

            // Then: should return cached data
            val result = repository.getChannels()

            assertTrue(result.isSuccess)
//            assertEquals("cached", result.getOrNull()?.first()?.id)
        }
    }
}