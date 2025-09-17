package com.theminesec.example.question1

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.theminesec.example.question1.feature.chat.ChatFragment
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertNotNull
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatFragmentLifecycleTest {

    @Test
    fun `fragment should not crash when destroyed during LiveData observation`() {
        // Use explicit theme to avoid R$style error
        val scenario = launchFragmentInContainer<ChatFragment>(
            themeResId = android.R.style.Theme_Material_Light,
            initialState = Lifecycle.State.RESUMED
        )

        var crashOccurred = false
        val originalHandler = Thread.getDefaultUncaughtExceptionHandler()

        // Set up crash detection
        Thread.setDefaultUncaughtExceptionHandler { _, exception ->
            if (exception is IllegalStateException &&
                exception.message?.contains("LifecycleOwner") == true) {
                crashOccurred = true
            }
            originalHandler?.uncaughtException(Thread.currentThread(), exception)
        }

        try {
            scenario.onFragment { fragment ->
                // Verify fragment is set up correctly
                assertNotNull(fragment.view)
                assertNotNull(fragment.viewLifecycleOwner)
            }

            // Simulate rapid navigation that destroys view
            scenario.moveToState(Lifecycle.State.STARTED)
            scenario.moveToState(Lifecycle.State.CREATED)
            scenario.moveToState(Lifecycle.State.DESTROYED)

            // Wait for any pending operations
            Thread.sleep(100)

            // Assert no crash occurred
            assertFalse("Fragment should not crash during destruction", crashOccurred)

        } finally {
            // Restore original exception handler
            Thread.setDefaultUncaughtExceptionHandler(originalHandler)
        }
    }

    @Test
    fun `fragment should handle rapid view creation and destruction`() {
        val scenario = launchFragmentInContainer<ChatFragment>(
            themeResId = android.R.style.Theme_Material_Light,
            initialState = Lifecycle.State.RESUMED
        )

        // Simulate rapid navigation back and forth
        repeat(3) {
            scenario.onFragment { fragment ->
                assertNotNull("Fragment view should be available", fragment.view)
            }

            // Destroy and recreate
            scenario.recreate()

            scenario.onFragment { fragment ->
                assertNotNull("Fragment should survive recreation", fragment.view)
            }
        }
    }

    @Test
    fun `fragment should properly cleanup observers on view destruction`() {
        val scenario = launchFragmentInContainer<ChatFragment>(
            themeResId = android.R.style.Theme_Material_Light,
            initialState = Lifecycle.State.RESUMED
        )

        lateinit var initialViewLifecycleOwner: LifecycleOwner

        scenario.onFragment { fragment ->
            initialViewLifecycleOwner = fragment.viewLifecycleOwner
            assertEquals(Lifecycle.State.RESUMED, initialViewLifecycleOwner.lifecycle.currentState)
        }

        // Move to destroyed state
        scenario.moveToState(Lifecycle.State.DESTROYED)

        // Verify lifecycle is properly destroyed
        assertEquals(
            "ViewLifecycleOwner should be destroyed",
            Lifecycle.State.DESTROYED,
            initialViewLifecycleOwner.lifecycle.currentState
        )
    }
}

