package com.titou.blaum.presentation.mainActivity

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.titou.blaum.presentation.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

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


        Espresso.onView(ViewMatchers.withId(R.id.search_menu_item)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            ViewActions.typeText(stringToBeTyped),
            ViewActions.closeSoftKeyboard()
        )
        Espresso.onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).check(
            ViewAssertions.matches(
                ViewMatchers.withText(
                    stringToBeTyped
                )
            )
        )
//        }
    }
}
