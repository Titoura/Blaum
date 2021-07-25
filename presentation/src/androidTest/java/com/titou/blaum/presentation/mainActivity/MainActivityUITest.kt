package com.titou.blaum.presentation.mainActivity

import android.content.Intent
import android.widget.AutoCompleteTextView
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.titou.blaum.presentation.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityUITest {

    private lateinit var stringToBeTyped: String

    @get:Rule
    val activityRule = ActivityScenarioRule<MainActivity>(MainActivity::class.java)

//        Intent(
//            InstrumentationRegistry.getInstrumentation().targetContext,
//            MainActivity::class.java
//        )
//    )

    @Before
    fun initValidString() {
        stringToBeTyped = "Espresso"
    }

    @Test
    fun changeText_sameActivity() {
//        var intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext, MainActivity::class.java)
//        intent.putExtra("something", "something")
//        var scenario = ActivityScenario.launch<MainActivity>(intent)
//        scenario.onActivity {


            onView(withId(R.id.search_menu_item)).perform(click())
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
//        }
    }
}
