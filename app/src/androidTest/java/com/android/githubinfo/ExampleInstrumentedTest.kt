package com.android.githubinfo

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.android.githubinfo.ui.MainActivity
import com.example.userpurchases.ViewAssertionEx
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val rule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setFragment() {
        val scenario = rule.scenario
        scenario.onActivity {
            it.configureAndShowFragment()
        }
    }

    @Test
    fun testData() {
        // when
        Espresso.onView(withId(R.id.etSearchUser))
            .perform(clearText(), typeText("android"), pressImeActionButton())
        Thread.sleep(3000L)
        Espresso.onView(withId(R.id.tvUserId)).check(matches(withText("android")))
        Espresso.onView(withId(R.id.rvProjects)).check(
            ViewAssertionEx.hasItemCountOfRecyclerView()
        )
    }
}