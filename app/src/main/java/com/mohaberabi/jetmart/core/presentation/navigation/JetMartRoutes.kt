package com.mohaberabi.jetmart.core.presentation.navigation

import android.webkit.WebView
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mohaberabi.jetmart.core.presentation.compose.JetMartWebView
import kotlinx.serialization.Serializable


@Serializable
data class WebViewRoute(val url: String)


fun NavGraphBuilder.webViewScreen() =
    composable<WebViewRoute> { entry ->
        val url = entry.toRoute<WebViewRoute>().url
        JetMartWebView(
            initialUr = url,
        )
    }


fun NavController.navigateToWebView(url: String) = navigate(WebViewRoute(url))