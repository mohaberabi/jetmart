package com.mohaberabi.jetmart.features.auth.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mohaberabi.jetmart.features.auth.presentation.screen.LoginScreenRoot
import com.mohaberabi.jetmart.features.home_layout.presentation.HomeLayoutRoute
import kotlinx.serialization.Serializable


@Serializable
data class LoginRoute(val canSkip: Boolean)

fun NavGraphBuilder.loginScreen(
    jetMartNavController: NavController,
    onShowSnackBar: (String) -> Unit
) = composable<LoginRoute> { entry ->
    val canSkip = entry.toRoute<LoginRoute>().canSkip
    LoginScreenRoot(
        canSkip = canSkip,
        onSkip = { jetMartNavController.popBackStack() },
        onGoBack = { jetMartNavController.popBackStack() },
        onGoHomeScreen = {
            jetMartNavController.popBackStack()
        },
        onShowSnackBar = onShowSnackBar
    )
}


fun NavController.navigateToLogin(
    canSkip: Boolean
) = navigate(LoginRoute(canSkip = canSkip))