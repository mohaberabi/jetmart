package com.mohaberabi.jetmart.features.home.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomItems
import com.mohaberabi.jetmart.features.address.presentation.navigation.navigateToAddressLocation
import com.mohaberabi.jetmart.features.address.presentation.navigation.showAddressPicker
import com.mohaberabi.jetmart.features.auth.presentation.navigation.navigateToLogin
import com.mohaberabi.jetmart.features.home.presentation.screen.HomeScreenRoot
import com.mohaberabi.jetmart.features.listing.presentation.navigation.navigateToListingScreen


fun NavGraphBuilder.homeScreen(
    jetMartNavController: NavController
) = composable(
    route = JetMartBottomItems.HOME.route,
) {
    HomeScreenRoot(
        onGoAddressLocation = { jetMartNavController.navigateToAddressLocation() },
        onCategoryClick = { id, cats -> jetMartNavController.navigateToListingScreen(id, cats) },
        onGoSignIn = { jetMartNavController.navigateToLogin(canSkip = false) },
        onShowAddressPicker = { jetMartNavController.showAddressPicker() }
    )
}
