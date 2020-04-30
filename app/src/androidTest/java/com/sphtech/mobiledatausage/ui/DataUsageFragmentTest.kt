package com.sphtech.mobiledatausage.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sphtech.mobiledatausage.R
import org.hamcrest.Matchers
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class DataUsageFragmentTest {

    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testDataUsageFragmentDisplay() {
        activityTestRule.activity.findViewById<RecyclerView>(R.id.data_usage_rv)
            ?.let { recyclerView ->
                recyclerView.adapter?.itemCount?.let { itemCount ->
                    if (itemCount == 0) {
                        assertThat(itemCount, Matchers.equalTo(0))
                        onView(withId(R.id.header_view)).check(
                            matches(
                                withEffectiveVisibility(
                                    Visibility.GONE
                                )
                            )
                        )
                    } else {
                        Assert.assertTrue(itemCount > 0)
                        onView(withId(R.id.header_view)).check(matches(isDisplayed()))
                        onView(withId(R.id.empty_notice)).check(
                            matches(
                                withEffectiveVisibility(
                                    Visibility.GONE
                                )
                            )
                        )
                    }
                }
            }
    }

}