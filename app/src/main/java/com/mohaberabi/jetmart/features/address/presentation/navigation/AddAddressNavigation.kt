package com.mohaberabi.jetmart.features.address.presentation.navigation

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.mohaberabi.jetmart.core.util.serializableType
import com.mohaberabi.jetmart.features.address.presentation.screen.AddAddressScreenRoot
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@Serializable
data class AddAddressRoute(
    val args: AddAddressRouteArgs
) {
    companion object {
        val typeMap =
            mapOf(
                typeOf<AddAddressRouteArgs>() to serializableType<AddAddressRouteArgs>(),
            )

        fun fromSavedStateHandle(savedStateHandle: SavedStateHandle) =
            savedStateHandle.toRoute<AddAddressRoute>(typeMap)
    }
}

@Parcelize
@Serializable
data class AddAddressRouteArgs(
    val locationName: String,
    val lat: Double,
    val lng: Double
) : Parcelable

fun NavGraphBuilder.addAddressScreen(
    jetMartNavController: NavController,
    onShowSnackBar: (String) -> Unit,
) = composable<AddAddressRoute>(
    typeMap = AddAddressRoute.typeMap
) {

    AddAddressScreenRoot(
        onShowSnackBar = onShowSnackBar,
        onBackClick = { jetMartNavController.popBackStack() }
    )
}

fun NavController.navigateToAddAddress(
    lat: Double,
    lng: Double,
    locationName: String
) = navigate(
    AddAddressRoute(
        args = AddAddressRouteArgs(
            lat = lat,
            lng = lng,
            locationName = locationName
        )
    )
) {
    popUpTo(graph.startDestinationId) {
        inclusive = false
        saveState = false
    }
    restoreState = false
}
