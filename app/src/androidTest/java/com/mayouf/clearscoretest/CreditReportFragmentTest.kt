package com.mayouf.clearscoretest

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.mayouf.clearscoretest.presentation.MainActivity
import com.mayouf.clearscoretest.presentation.creditreport.CreditReportFragment
import org.junit.Before
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters


@RunWith(AndroidJUnit4::class)
@LargeTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class CreditReportFragmentTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java).onActivity {
            it.supportFragmentManager.beginTransaction().replace(
                R.id.fragmentContainer,
                CreditReportFragment.newInstance(),
                CreditReportFragment.FRAGMENT_NAME
            ).commit()
        }
    }

    @Test
    fun testACheckProgressBar() {
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

    @Test
    fun testBCheckScoreValueIsDisplayed() {
        Thread.sleep(2000)
        onView(withId(R.id.donut__credit_score_value)).check(matches(isDisplayed()))
        onView(withId(R.id.donut__credit_score_value)).check(matches(withText("514")))
    }

    @Test
    fun testCCheckDonutViewIsDisplayed() {
        Thread.sleep(2000)
        onView(withId(R.id.donut_view)).check(matches(isDisplayed()))
    }
}