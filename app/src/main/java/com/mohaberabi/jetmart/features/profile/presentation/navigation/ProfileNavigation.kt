package com.mohaberabi.jetmart.features.profile.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.profile.presentation.screen.ProfileScreenRoot
import kotlinx.serialization.Serializable


@Serializable
object ProfileRoute


fun NavGraphBuilder.profileScreen(
    navController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<ProfileRoute> {
    ProfileScreenRoot(
        onGoBack = { navController.popBackStack() },
        onShowSnackBar = onShowSnackBar
    )
}

fun NavController.navigateToProfileScreen() = navigate(ProfileRoute)