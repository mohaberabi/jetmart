package com.mohaberabi.jetmart.core.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


fun Map<String, String>.translate(
    local: String,
): String = this[local] ?: ""

