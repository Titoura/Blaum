package com.titou.blaum.Utils

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher

object Matchers {
    fun recyclerViewSizeMatcher(matcherSize: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("List size: $matcherSize")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                return matcherSize == recyclerView.adapter!!.itemCount
            }
        }
    }

    fun lastVisibleChildMatcher(firstVisibleChildPosition: Int, maxDifference: Int): Matcher<View?> {
        return object : BoundedMatcher<View?, RecyclerView>(RecyclerView::class.java) {
            override fun describeTo(description: Description) {
                description.appendText("First visible child: $firstVisibleChildPosition")
            }

            override fun matchesSafely(recyclerView: RecyclerView): Boolean {
                val layoutManager = (recyclerView.layoutManager as LinearLayoutManager)
                val position = layoutManager.findLastVisibleItemPosition()
                return kotlin.math.abs(firstVisibleChildPosition - position) < maxDifference
            }
        }
    }

}