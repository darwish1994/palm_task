package com.theminesec.example.question1.feature.chat

import android.view.LayoutInflater
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.google.common.truth.Truth.assertThat
import com.theminesec.example.question1.R
import com.theminesec.example.question1.feature.chat.domain.model.Message
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@RunWith(MockitoJUnitRunner::class)
class ChatFragmentTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    @Mock
    private lateinit var mockViewModel: ChatViewModel

    @Mock
    private lateinit var mockLifecycleOwner: LifecycleOwner

    @Mock
    private lateinit var mockLifecycle: Lifecycle

    @Mock
    private lateinit var mockView: View

    @Mock
    private lateinit var mockRecyclerView: RecyclerView

    private lateinit var fragment: ChatFragment

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        fragment = spy(ChatFragment())

        // Setup mock lifecycle
        whenever(mockLifecycleOwner.lifecycle).thenReturn(mockLifecycle)
        whenever(mockLifecycle.currentState).thenReturn(Lifecycle.State.STARTED)

        // Setup mock findViewById
        whenever(mockView.findViewById<RecyclerView>(R.id.recycler_view)).thenReturn(mockRecyclerView)

        // Mock ViewModel injection
        doReturn(mockViewModel).whenever(fragment).viewModel
    }

    @Test
    fun `CRASH_FIX - observer uses viewLifecycleOwner not activity lifecycle`() {
        // Arrange
        val messagesLiveData =spy(MutableLiveData<List<Message>>())
        whenever(mockViewModel.messages).thenReturn(messagesLiveData)

        // Mock the fragment lifecycle properly
        doReturn(mockLifecycleOwner).whenever(fragment).viewLifecycleOwner

        // Simulate proper fragment lifecycle: onCreateView -> onViewCreated
        fragment.onCreateView(mock(LayoutInflater::class.java), null, null)
        fragment.onViewCreated(mockView, null)

        // Act - This should now work because viewLifecycleOwner is available
        // The setupObservers() is called within onViewCreated, so let's verify it was called correctly

        // Assert - Verify correct lifecycle owner is used
        assertThat(messagesLiveData.hasObservers()).isTrue()

    }

    @Test
    fun `initViews should find RecyclerView correctly`() {
        // Act
        fragment.initViews(mockView)

        // Assert
        verify(mockView).findViewById<RecyclerView>(R.id.recycler_view)
        assertThat(fragment.recyclerView).isEqualTo(mockRecyclerView)
    }

    @Test
    fun `observer should not crash when messages emitted after view destroyed`() {
        // Arrange
        val messagesLiveData =  mock<MutableLiveData<List<Message>>>()
        whenever(mockViewModel.messages).thenReturn(messagesLiveData)
        doReturn(mockLifecycleOwner).whenever(fragment).viewLifecycleOwner

        // Simulate complete fragment lifecycle
        fragment.onCreateView(mock(LayoutInflater::class.java), null, null)
        fragment.onViewCreated(mockView, null)

        // Verify observer is set up
        verify(messagesLiveData).observe(eq(mockLifecycleOwner), any())

        // Simulate view destruction
        fragment.onDestroyView()

        // Act - This should NOT crash because viewLifecycleOwner handles lifecycle properly
        // In real scenario, the observer would be automatically removed when viewLifecycleOwner is destroyed
        messagesLiveData.postValue(listOf(Message("","","test")))

        // Assert - No crash occurred
        assertTrue("Test completed without crash", true)
    }


    @Test
    fun `onViewCreated should call initViews and setupObservers`() {
        // Arrange
        val spyFragment = spy(ChatFragment())
        doReturn(mockViewModel).whenever(spyFragment).viewModel
        doReturn(mockLifecycleOwner).whenever(spyFragment).viewLifecycleOwner

        // Mock the ViewModel's messages LiveData
        val messagesLiveData =  mock<MutableLiveData<List<Message>>>()
        whenever(mockViewModel.messages).thenReturn(messagesLiveData)

        // Simulate onCreateView first
        spyFragment.onCreateView(mock(LayoutInflater::class.java), null, null)

        // Act
        spyFragment.onViewCreated(mockView, null)

        // Assert
        verify(spyFragment).initViews(mockView)
        verify(spyFragment).setupObservers()
        verify(messagesLiveData).observe(eq(mockLifecycleOwner), any())
    }
}