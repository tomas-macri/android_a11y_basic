package com.tomasmacri.accessibilitybasiccompose.ui.screens.webview

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.tomasmacri.accessibilitybasiccompose.ui.BasePreview

@Composable
fun WebViewScreenRoot() {
    WebViewScreen()
}

@Composable
private fun WebViewScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(factory = { context ->
            WebView(context).also {
                it.webViewClient = WebViewClient()
            }
        }, update = {
            it.loadUrl("https://www.suressedirektbank.de/de/app-products-accordion")
        })
    }
}

@Preview
@Composable
fun PreviewWebView() {
    BasePreview {
        WebViewScreen()
    }
}