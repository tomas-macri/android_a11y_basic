package com.tomasmacri.accessibilitybasicviews.ui

import androidx.test.platform.app.InstrumentationRegistry
import com.deque.mobile.devtools.AxeDevTools
import org.junit.Before
import org.junit.Test

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleTestAxeTest {

    private val axe = AxeDevTools()

    init {
        // Connect using an API key
        axe.loginWithApiKey(
            "valid_axe_api_key",
        )
    }

    @Before
    fun setup() {
        // Pass the information registry to axe DevTools
        axe.setInstrumentation(InstrumentationRegistry.getInstrumentation())

        // Optional: Add tags or utilize other customization features here
        axe.tagScanAs(setOf("Example"))
    }

    @Test
    fun foobar() {
        // Scan the app for accessibility issues and upload to the dashboard
        axe.scan()?.uploadToDashboard()
        axe.tearDown()
    }
}