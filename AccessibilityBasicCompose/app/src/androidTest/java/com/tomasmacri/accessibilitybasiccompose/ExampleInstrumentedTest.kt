package com.tomasmacri.accessibilitybasiccompose

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tomasmacri.accessibilitybasiccompose.ui.screens.add_coin.AddCoin
import com.tomasmacri.accessibilitybasiccompose.ui.theme.AccessibilityBasicComposeTheme

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.tomasmacri.accessibilitybasiccompose", appContext.packageName)
    }
}


class MyComposeTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun printSemanticsTreeInLog() {
        // Start the app
        composeTestRule.setContent {
            AccessibilityBasicComposeTheme  {
                AddCoin(
                    false,
                    {},
                    {}
                )
            }
        }
        // Log the full semantics tree
        composeTestRule.onRoot().printToLog("SEMANTICS TREE LOG")
    }
}