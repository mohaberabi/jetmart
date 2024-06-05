package com.mohaberabi.jetmart.features.order.listing.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.order.listing.presentation.screens.OrderListingScreenRoot
import com.mohaberabi.jetmart.features.order.tracking.presenation.navigation.navigateToOrderTracking
import kotlinx.serialization.Serializable


@Serializable
object OrderListingRoute


fun NavGraphBuilder.ordersListingScreen(
    jetMartNavController: NavController,
) = composable<OrderListingRoute> {

    OrderListingScreenRoot(
        onGoBack = { jetMartNavController.popBackStack() },
        onGoTrackOrder = { jetMartNavController.navigateToOrderTracking(it) }
    )
}


fun NavController.navigateToOrdersListing() = navigate(OrderListingRoute)