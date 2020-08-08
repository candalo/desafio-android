package com.picpay.desafio.android.view

import android.view.View
import android.widget.ProgressBar
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertTrue
import org.junit.Test

class ViewExtensionsKtTest {

    private val context = InstrumentationRegistry.getInstrumentation().targetContext
    private val progressBar = ProgressBar(context)

    @Test
    fun progressBarOnVisibleState() {
        with(progressBar) {
            visible()
            assertTrue(visibility == View.VISIBLE)
        }
    }

    @Test
    fun progressBarOnGoneState() {
        with(progressBar) {
            gone()
            assertTrue(visibility == View.GONE)
        }
    }
}