package com.mohaberabi.jetmart.jetmart.compose

import JetMartScaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SnackbarDuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.navigation.JetMartNavHost
import com.mohaberabi.jetmart.features.auth.presentation.navigation.LoginRoute
import com.mohaberabi.jetmart.features.home_layout.presentation.HomeLayoutRoute
import com.mohaberabi.jetmart.jetmart.activity.viewmodel.JetMartActivityViewModel
import kotlinx.coroutines.launch


@Composable
fun JetMartComposeApp(
    modifier: Modifier = Modifier,
    jetMartState: JetMartState,
    viewModel: JetMartActivityViewModel
) {
    val noNetworkString =
        stringResource(R.string.network_lost)
    val activityState by viewModel.state.collectAsStateWithLifecycle()
    val hasNetwork by viewModel.hasNetwork.collectAsStateWithLifecycle()
    LaunchedEffect(
        key1 = hasNetwork
    ) {
        if (!hasNetwork) {
            jetMartState.snackbarHostState.showSnackbar(
                noNetworkString,
                duration = SnackbarDuration.Indefinite
            )
        }

    }
    val navController = jetMartState.navController
    val snackBarHostState = jetMartState.snackbarHostState
    val scope = jetMartState.scope
    if (activityState.didLoadData) {
        JetMartScaffold(
            modifier = modifier,
            snackBarHostState = snackBarHostState,
        ) {
            JetMartNavHost(
                onShowSnackBar = { message ->
                    scope.launch {
                        snackBarHostState.showSnackbar(message)
                    }
                },
                navController = navController,
                startRoute = HomeLayoutRoute,
            )
        }

    }

}