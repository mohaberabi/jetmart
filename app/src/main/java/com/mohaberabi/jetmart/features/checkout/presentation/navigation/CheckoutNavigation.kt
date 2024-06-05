package com.mohaberabi.jetmart.features.checkout.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.checkout.presentation.screen.CheckoutScreenRoot
import com.mohaberabi.jetmart.features.order.tracking.presenation.navigation.navigateToOrderTracking
import kotlinx.serialization.Serializable


@Serializable
object CheckoutRoute


fun NavGraphBuilder.checkoutScreen(
    onShowSnackBar: (String) -> Unit,
    jetMartNavController: NavController,
) = composable<CheckoutRoute> {
    CheckoutScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoOrderTracking = { jetMartNavController.navigateToOrderTracking(it) }
    )
}

fun NavController.navigateToCheckoutScreen() = navigate(CheckoutRoute)