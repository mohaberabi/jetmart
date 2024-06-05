package com.mohaberabi.jetmart.features.cart.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.cart.presentation.screen.CartScreenRoot
import com.mohaberabi.jetmart.features.checkout.presentation.navigation.navigateToCheckoutScreen
import kotlinx.serialization.Serializable

@Serializable
object CartRoute


fun NavGraphBuilder.cartScreen(
    jetMartNavController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<CartRoute> {
    CartScreenRoot(
        onBackClick = { jetMartNavController.popBackStack() },
        onShowSnackBar = onShowSnackBar,
        onGoConfirmOrder = { jetMartNavController.navigateToCheckoutScreen() },
    )
}

fun NavController.navigateToCartScreen() =
    navigate(CartRoute) {
        restoreState = true
    }