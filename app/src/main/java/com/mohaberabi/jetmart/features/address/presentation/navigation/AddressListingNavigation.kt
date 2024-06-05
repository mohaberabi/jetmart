package com.mohaberabi.jetmart.features.address.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import com.mohaberabi.jetmart.features.address.presentation.compose.AddressPickerDialogRoot
import com.mohaberabi.jetmart.features.address.presentation.screen.AddressListingScreenRoot
import kotlinx.serialization.Serializable

@Serializable

object AddressBookRoute


@Serializable

object AddressPickerRoute

fun NavGraphBuilder.addressBookScreen(
    jetMartNavController: NavController,
) = composable<AddressBookRoute> {
    AddressListingScreenRoot(
        onGoAddAddress = { jetMartNavController.navigateToAddressLocation() },
        onBackClick = { jetMartNavController.popBackStack() },
    )
}

fun NavGraphBuilder.addressPickerDialog(
    jetMartNavController: NavController,
) = dialog<AddressPickerRoute> {
    AddressPickerDialogRoot(
        onAddAddress = { jetMartNavController.navigateToAddressLocation() },
        onDismiss = { jetMartNavController.popBackStack() }
    )
}

fun NavController.showAddressPicker() = navigate(AddressPickerRoute)
fun NavController.goToAddressBook() = navigate(AddressBookRoute)