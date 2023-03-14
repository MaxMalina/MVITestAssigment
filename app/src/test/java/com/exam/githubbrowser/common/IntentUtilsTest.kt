package com.exam.githubbrowser.common

import android.content.Intent
import android.net.Uri
import com.exam.githubbrowser.data.common.buildUrlIntent
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IntentUtilsTest {

    @Test
    fun `when building a url intent then return a valid intent`() {
        val url = "http://www.google.com"
        val urlUri = Uri.parse(url)

        val intent = buildUrlIntent(url)

        Assert.assertEquals(Intent.ACTION_VIEW, intent.action)
        Assert.assertEquals(urlUri, intent.data)
    }

}