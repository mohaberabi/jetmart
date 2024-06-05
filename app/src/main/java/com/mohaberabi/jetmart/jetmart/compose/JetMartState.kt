package com.mohaberabi.jetmart.jetmart.compose

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

data class JetMartState(
    val navController: NavHostController,
    val scope: CoroutineScope,
    val snackbarHostState: SnackbarHostState,
)

@Composable
fun rememberJetMartState(): JetMartState {
    val sibhaNavController = rememberNavController()
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    return remember(
        sibhaNavController,
        scope,
        snackbarHostState,
    ) {
        JetMartState(
            navController = sibhaNavController,
            scope = scope,
            snackbarHostState = snackbarHostState,
        )
    }
}