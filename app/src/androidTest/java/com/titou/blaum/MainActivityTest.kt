package com.titou.blaum

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.titou.blaum.Utils.Matchers.lastVisibleChildMatcher
import com.titou.blaum.Utils.Matchers.recyclerViewSizeMatcher
import com.titou.blaum.Utils.OrientationChangeAction.Companion.orientationLandscape
import com.titou.blaum.presentation.R
import com.titou.blaum.presentation.mainActivity.MainActivity
import com.titou.blaum.presentation.mainActivity.TitleAdapter
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


//TODO: move to presentation module
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var stringToBeTyped: String
    private val testScrollPosition = 100
    private val defaultThreadSleepTime = 5000L

    // Here we are in a static case, but this would require inputing a known amount of items in a real app
    private val defaultTitlesSize = 5000

    // We have to allow this buffer because landscape view usually displays less items at a time
    private val rotationListPositionMaxDifference = 5

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun initValidString() {
        stringToBeTyped = "This is a test"
    }

    @Test
    fun click_on_filter_and_type_some_text() {
        Thread.sleep(defaultThreadSleepTime)
        onView(withId(R.id.search_menu_item)).perform(click())
        Thread.sleep(defaultThreadSleepTime)
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            typeText(stringToBeTyped),
            closeSoftKeyboard()
        )
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).check(
            matches(
                withText(
                    stringToBeTyped
                )
            )
        )
    }

    @Test
    fun check_scrollview_position_after_rotation() {

        Thread.sleep(defaultThreadSleepTime)
        onView(withId(R.id.recyclerview))
            .perform(
                scrollToPosition<TitleAdapter.TitleViewHolder>(testScrollPosition)
            )
        onView(isRoot()).perform(orientationLandscape())

        Thread.sleep(defaultThreadSleepTime)
        onView(withId(R.id.recyclerview))
            .check(
                matches(
                    lastVisibleChildMatcher(testScrollPosition, rotationListPositionMaxDifference)
                )
            )

    }

    @Test
    fun check_recyclerview_children_size() {

        Thread.sleep(defaultThreadSleepTime)
        onView(withId(R.id.recyclerview))
            .check(matches(recyclerViewSizeMatcher(defaultTitlesSize)))

    }
}

