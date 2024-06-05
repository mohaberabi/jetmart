package com.mohaberabi.jetmart.features.home_layout.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomItems
import com.mohaberabi.jetmart.features.auth.presentation.navigation.navigateToLogin
import com.mohaberabi.jetmart.features.cart.presentation.navigation.navigateToCartScreen
import com.mohaberabi.jetmart.features.home.presentation.navigation.homeScreen

import com.mohaberabi.jetmart.features.settings.presentation.navigation.settingsScreen
import kotlinx.serialization.Serializable

@Serializable
object HomeLayoutRoute


@Composable
fun HomeLayoutNavHost(
    modifier: Modifier = Modifier,
    jetMartNavController: NavHostController,
    layoutNavController: NavHostController,
    onShowSnackBar: (String) -> Unit
) {

    NavHost(
        modifier = modifier,
        navController = layoutNavController,
        startDestination = JetMartBottomItems.HOME.route
    ) {
        homeScreen(
            jetMartNavController = jetMartNavController,
        )

        settingsScreen(
            jetMartNavController = jetMartNavController
        )
    }

}

fun NavGraphBuilder.homeLayout(
    jetMartNavController: NavHostController,
    layoutNavController: NavHostController,
    onShowSnackBar: (String) -> Unit,
) = composable<HomeLayoutRoute> {
    val navBackstackEntry by layoutNavController.currentBackStackEntryAsState()
    val currentRoute =
        navBackstackEntry?.destination?.route ?: JetMartBottomItems.HOME.route
    HomeLayoutRoot(
        dynamicContent = { padding ->
            HomeLayoutNavHost(
                modifier = Modifier.padding(padding),
                jetMartNavController = jetMartNavController,
                layoutNavController = layoutNavController,
                onShowSnackBar = onShowSnackBar
            )
        },
        onShowSnackBar = onShowSnackBar,
        onGoSignIn = { jetMartNavController.navigateToLogin(canSkip = false) },
        onGoToCart = { jetMartNavController.navigateToCartScreen() },
        onBottomNavigated = { layoutNavController.navigateBottom(it) },
        currentBottomRoute = currentRoute
    )
}

fun NavController.navigateToHomeLayout() {
    popBackStack(graph.startDestinationId, true)
    navigate(HomeLayoutRoute)
}

fun NavController.navigateBottom(
    item: JetMartBottomItems,
) =
    navigate(item.route) {
        popUpTo(currentBackStackEntry!!.destination.route!!) {
            inclusive = true
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }



