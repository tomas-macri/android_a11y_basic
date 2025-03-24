package com.tomasmacri.accessibilitybasicviews.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.accessibility.AccessibilityChecks
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.google.android.apps.common.testing.accessibility.framework.AccessibilityCheckResult
import com.tomasmacri.accessibilitybasicviews.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
@LargeTest
class MainInstrumentedTest {
    init {
        AccessibilityChecks.enable().setRunChecksFromRootView(true).setThrowExceptionFor(AccessibilityCheckResult.AccessibilityCheckResultType.NOT_RUN)
    }

    @get:Rule
    val activityScenarioRoule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun btnBigChangeClick() {
        Espresso.onView(withId(R.id.btnBigChange))
            .perform(ViewActions.click())
    }
}