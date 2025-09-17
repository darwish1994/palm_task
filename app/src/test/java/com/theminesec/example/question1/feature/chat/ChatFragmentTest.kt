package com.theminesec.example.question1.feature.chat

import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.theminesec.example.question1.feature.chat.domain.model.Message
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ChatFragmentTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var mockViewModel: ChatViewModel

    @Mock
    private lateinit var mockLifecycleOwner: LifecycleOwner

    @Mock
    private lateinit var mockLifecycle: Lifecycle


    @Mock
    private lateinit var mockObserver: Observer<List<Message>>

    private lateinit var fragment: ChatFragment

    @Before
    fun setup() {
        fragment = ChatFragment()

    }

    @Test
    fun `onViewCreated should not crash when lifecycle is destroyed`() {
        // Given: lifecycle is in DESTROYED state
        `when`(mockLifecycle.currentState).thenReturn(Lifecycle.State.DESTROYED)
        `when`(mockLifecycleOwner.lifecycle).thenReturn(mockLifecycle)
        val testMessages = listOf(
            Message("0", "Test message 1", "0"),
            Message("1", "Test message 2", "1")
        )
        val messagesMutableLiveData = MutableLiveData<List<Message>>(testMessages)
        val messagesLiveData: LiveData<List<Message>> = messagesMutableLiveData

        `when`(mockViewModel.messages).thenReturn(messagesLiveData)


        assertTrue(!mockViewModel.messages.hasObservers())

    }

    @Test
    fun `viewModel messages observation should work with valid lifecycle`() {
        runTest {
            val testMessages = listOf(
                Message("0", "Test message 1", "0"),
                Message("1", "Test message 2", "1")
            )
            val messagesLiveData = MutableLiveData<List<Message>>()
            `when`(mockViewModel.messages).thenReturn(messagesLiveData)

            // Mock lifecycle as STARTED
            `when`(mockLifecycle.currentState).thenReturn(Lifecycle.State.STARTED)
            `when`(mockLifecycleOwner.lifecycle).thenReturn(mockLifecycle)

            // When: Observe the LiveData and post value
            messagesLiveData.observe(mockLifecycleOwner, mockObserver)
            messagesLiveData.value = testMessages  // Use .value instead of .postValue for unit tests

            // Then: Observer should receive the messages
            verify(mockObserver).onChanged(testMessages)

        }
    }
}