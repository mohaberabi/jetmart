package com.mohaberabi.jetmart.core.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable


@Composable
fun JetMartTheme(
    langCode: String = "en",
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme = jetMartColorScheme,
        typography = jetMarTypography(langCode),
        content = content
    )
}