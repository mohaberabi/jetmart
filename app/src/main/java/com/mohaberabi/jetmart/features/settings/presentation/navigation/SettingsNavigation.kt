package com.mohaberabi.jetmart.features.settings.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomItems
import com.mohaberabi.jetmart.core.presentation.navigation.navigateToWebView
import com.mohaberabi.jetmart.features.address.presentation.navigation.goToAddressBook
import com.mohaberabi.jetmart.features.auth.presentation.navigation.navigateToLogin
import com.mohaberabi.jetmart.features.order.listing.presentation.navigation.navigateToOrdersListing
import com.mohaberabi.jetmart.features.profile.presentation.navigation.navigateToProfileScreen
import com.mohaberabi.jetmart.features.settings.presentation.screen.SettingsScreenRoot

private const val TEMP_URL = "https://google.com"
fun NavGraphBuilder.settingsScreen(
    jetMartNavController: NavController
) =
    composable(
        route = JetMartBottomItems.Settings.route
    ) {
        SettingsScreenRoot(
            onGoProfile = { jetMartNavController.navigateToProfileScreen() },
            onGoOnBoarding = { jetMartNavController.navigateToLogin(canSkip = true) },
            onGoAddressBook = { jetMartNavController.goToAddressBook() },
            onGoSignIn = { jetMartNavController.navigateToLogin(canSkip = false) },
            onGoPrivacy = { jetMartNavController.navigateToWebView(TEMP_URL) },
            onGoTerms = { jetMartNavController.navigateToWebView(TEMP_URL) },
            onGoOrders = { jetMartNavController.navigateToOrdersListing() }
        )
    }