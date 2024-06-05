package com.mohaberabi.jetmart.core.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.mohaberabi.jetmart.features.address.presentation.navigation.addressLocationScreen
import com.mohaberabi.jetmart.features.address.presentation.navigation.addAddressScreen
import com.mohaberabi.jetmart.features.address.presentation.navigation.addressBookScreen
import com.mohaberabi.jetmart.features.address.presentation.navigation.addressPickerDialog
import com.mohaberabi.jetmart.features.auth.presentation.navigation.loginScreen
import com.mohaberabi.jetmart.features.cart.presentation.navigation.cartScreen
import com.mohaberabi.jetmart.features.checkout.presentation.navigation.checkoutScreen
import com.mohaberabi.jetmart.features.home_layout.presentation.homeLayout
import com.mohaberabi.jetmart.features.item.presentation.navigation.itemScreen
import com.mohaberabi.jetmart.features.listing.presentation.navigation.listingScreen
import com.mohaberabi.jetmart.features.order.listing.presentation.navigation.ordersListingScreen
import com.mohaberabi.jetmart.features.order.tracking.presenation.navigation.orderTrackingScreen
import com.mohaberabi.jetmart.features.profile.presentation.navigation.profileScreen


@Composable
fun JetMartNavHost(
    modifier: Modifier = Modifier,
    onShowSnackBar: (String) -> Unit = {},
    navController: NavHostController,
    startRoute: Any,
) {
    val homeLayoutNavController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startRoute
    ) {
        homeLayout(
            jetMartNavController = navController,
            layoutNavController = homeLayoutNavController,
            onShowSnackBar = onShowSnackBar,
        )
        loginScreen(
            jetMartNavController = navController,
            onShowSnackBar = onShowSnackBar
        )

        profileScreen(
            navController = navController,
            onShowSnackBar = onShowSnackBar
        )
        listingScreen(
            jetMartNavController = navController,
        )
        cartScreen(
            jetMartNavController = navController,
            onShowSnackBar = onShowSnackBar
        )
        itemScreen(
            jetMartNavController = navController,
            onShowSnackBar = onShowSnackBar
        )
        addressLocationScreen(
            jetMartNavController = navController,
            onShowSnackBar = onShowSnackBar
        )
        addAddressScreen(
            jetMartNavController = navController,
            onShowSnackBar = onShowSnackBar
        )

        addressPickerDialog(
            jetMartNavController = navController
        )
        checkoutScreen(
            jetMartNavController = navController,
            onShowSnackBar = onShowSnackBar
        )
        addressBookScreen(
            jetMartNavController = navController
        )
        orderTrackingScreen(
            jetMartNavController = navController,
        )
        webViewScreen(

        )
        ordersListingScreen(
            jetMartNavController = navController
        )
    }
}