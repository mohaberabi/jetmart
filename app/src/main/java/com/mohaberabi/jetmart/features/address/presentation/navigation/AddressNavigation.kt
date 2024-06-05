package com.mohaberabi.jetmart.features.address.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.mohaberabi.jetmart.features.address.presentation.screen.AddressLocationScreenRoot
import kotlinx.serialization.Serializable


@Serializable
object AddressLocationScreen


fun NavGraphBuilder.addressLocationScreen(
    onShowSnackBar: (String) -> Unit,
    jetMartNavController: NavController,
) = composable<AddressLocationScreen> {
    AddressLocationScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onGoSaveAddress = { location, geoCoded ->
            jetMartNavController.navigateToAddAddress(
                lat = location.lat,
                lng = location.lng,
                locationName = geoCoded
            )
        }
    )
}

fun NavController.navigateToAddressLocation() = navigate(AddressLocationScreen)