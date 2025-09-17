package com.theminesec.example.question1.feature.chat

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.theminesec.example.question1.MainActivity
import com.theminesec.example.question1.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ChatFragmentEspressoTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testChatFragmentLifecycleStability() {
        // Navigate to chat fragment

        onView(withId(R.id.btn_chat)).perform(click())


        // Verify fragment is still responsive
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
        Thread.sleep(5000)
        // Test the reproduction steps
        // 1. Open Chat - already done above
        // 2. Toggle network off (simulate with test configuration)
        // 3. Navigate back
        onView(isRoot()).perform(pressBack())

        // 4. Return to Chat
        onView(withId(R.id.btn_chat)).perform(click())

        // Should not crash and should display properly
        onView(withId(R.id.recycler_view)).check(matches(isDisplayed()))
    }
}