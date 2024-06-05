package com.mohaberabi.jetmart.features.order.tracking.presenation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.home_layout.presentation.HomeLayoutRoute
import com.mohaberabi.jetmart.features.order.tracking.presenation.screen.OrderTrackingScreenRoot
import kotlinx.serialization.Serializable


@Serializable
data class OrderTrackingRoute(
    val orderId: String,
)


fun NavGraphBuilder.orderTrackingScreen(
    jetMartNavController: NavController
) = composable<OrderTrackingRoute> {
    OrderTrackingScreenRoot(
        onShowSnackBar = {},
        onGoBack = { jetMartNavController.popBackStack() }
    )
}

fun NavController.navigateToOrderTracking(
    orderId: String,
) = navigate(OrderTrackingRoute(orderId)) {
    popUpTo(HomeLayoutRoute) {
        inclusive = false
        saveState = false
    }
    restoreState = true
}