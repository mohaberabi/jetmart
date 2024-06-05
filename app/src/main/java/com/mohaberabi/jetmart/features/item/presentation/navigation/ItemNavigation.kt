package com.mohaberabi.jetmart.features.item.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.auth.presentation.navigation.navigateToLogin
import com.mohaberabi.jetmart.features.cart.presentation.navigation.navigateToCartScreen
import com.mohaberabi.jetmart.features.item.presentation.screen.ItemScreenRoot
import kotlinx.serialization.Serializable


@Serializable
data class ItemRoute(val itemId: String)


fun NavGraphBuilder.itemScreen(
    jetMartNavController: NavController,
    onShowSnackBar: (String) -> Unit,
) =
    composable<ItemRoute> {
        ItemScreenRoot(
            onBackClick = { jetMartNavController.popBackStack() },
            onGoSignIn = { jetMartNavController.navigateToLogin(canSkip = false) },
            onGoCart = { jetMartNavController.navigateToCartScreen() },
            onShowSnackBar = onShowSnackBar
        )
    }

fun NavController.navigateToItemScreen(
    itemId:
    String
) = navigate(ItemRoute(itemId))