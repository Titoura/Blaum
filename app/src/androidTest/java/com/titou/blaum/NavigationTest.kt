package com.titou.blaum

import android.widget.AutoCompleteTextView
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollToPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.titou.blaum.Utils.Matchers.lastVisibleChildMatcher
import com.titou.blaum.Utils.Matchers.recyclerViewSizeMatcher
import com.titou.blaum.Utils.OrientationChangeAction.Companion.orientationLandscape
import com.titou.blaum.presentation.R
import com.titou.blaum.presentation.mainActivity.MainActivity
import com.titou.blaum.presentation.titlesList.TitleAdapter
import com.titou.blaum.presentation.titlesList.TitlesListFragment
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


//TODO: move to presentation module
@RunWith(AndroidJUnit4::class)
class NavigationTest {

    private lateinit var navController: TestNavHostController
    private val defaultThreadSleepTime = 5000L

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

//    @Before
//    fun initNavController() {
//        navController = TestNavHostController(
//            ApplicationProvider.getApplicationContext()
//        )
//
//    }

    @Test
    fun check_title_details_opens_and_closes_properly() {

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        //Before
        val titleScenario = launchFragmentInContainer<TitlesListFragment>()
        titleScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        //Test
        Thread.sleep(defaultThreadSleepTime)
        onView(withId(R.id.recyclerview))
            .perform(
                actionOnItemAtPosition<TitleAdapter.TitleViewHolder>(3, click())
            )

        Thread.sleep(defaultThreadSleepTime)
        assert(navController.currentDestination?.id == R.id.titleDetailsFragment)


        onView(withContentDescription(R.string.abc_action_bar_up_description)).perform(click())
        Thread.sleep(defaultThreadSleepTime)
        assert(navController.currentDestination?.id == R.id.titlesListFragment)

    }
}

