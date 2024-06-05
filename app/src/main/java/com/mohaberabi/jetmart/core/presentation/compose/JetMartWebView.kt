package com.mohaberabi.jetmart.core.presentation.compose

import JetMartScaffold
import android.view.ViewGroup
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JetMartWebView(
    modifier: Modifier = Modifier,
    initialUr: String
) {

    JetMartScaffold(
        topAppBar = {
            JetMartAppBar(
                showBackButton = true,
                onBackClick = {},
            )
        },
    ) { padding ->
        AndroidView(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            factory = {
                WebView(it).apply {
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    this.webViewClient = WebViewClient()
                    settings.loadWithOverviewMode = true
                    settings.useWideViewPort = true
                    settings.setSupportZoom(true)
                }
            },
            update = { webView ->
                webView.loadUrl(initialUr)
            }
        )
    }


}

