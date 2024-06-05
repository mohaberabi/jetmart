package com.mohaberabi.jetmart.features.address.presentation.screen

import android.Manifest
import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.GoogleMapComposable
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAlertDialog
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.extensions.isLocationPermissionsAllowed
import com.mohaberabi.jetmart.core.util.extensions.shouldShowLocationPermissionRationale
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.location.AddressLocationActions
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.location.AddressLocationState
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.location.AddressLocationViewModel
import com.mohaberabi.jetmart.core.domain.location.JetMartLocation
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.location.AddressLocationEvents
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddressLocationScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AddressLocationViewModel = koinViewModel(),
    onGoSaveAddress: (JetMartLocation, String) -> Unit,
    onShowSnackBar: (String) -> Unit
) {


    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is AddressLocationEvents.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    AddressLocationScreen(
        modifier = modifier,
        onAction = { action ->
            if (action is AddressLocationActions.OnSaveAddress) {
                onGoSaveAddress(
                    action.location,
                    action.geocodedLocation
                )
            } else {
                viewModel.onAction(action)
            }
        },
        state = state
    )

}

@Composable
fun AddressLocationScreen(
    modifier: Modifier = Modifier,
    onAction: (AddressLocationActions) -> Unit,
    state: AddressLocationState,
) {


    val context = LocalContext.current
    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestMultiplePermissions(),
        ) { perms ->
            val acceptedCoarse = perms[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            val acceptedFine = perms[Manifest.permission.ACCESS_FINE_LOCATION] == true
            val activity = context as ComponentActivity
            val showLocationRational = activity.shouldShowLocationPermissionRationale()
            onAction(
                AddressLocationActions.SubmitLocationPermission(
                    showRational = showLocationRational
                )
            )

        }

    LaunchedEffect(
        key1 = true,
    ) {
        val activity = context as ComponentActivity
        val showLocationRational = activity.shouldShowLocationPermissionRationale()
        onAction(
            AddressLocationActions.SubmitLocationPermission(
                showRational = showLocationRational
            )
        )
        if (!showLocationRational) {
            permissionLauncher.requestLocations(context)
        }
    }


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        GoogleMapComposable(
            currentLocation = state.currentLocation,
            onMyLocationButtonClicked = {
                onAction(AddressLocationActions.OnMyLocationPressed)
            },
            onLocationChanged = {
                onAction(AddressLocationActions.OnLocationChanged(it))
            },
        )

        ElevatedCard(
            colors = CardDefaults
                .elevatedCardColors(
                    containerColor = MaterialTheme
                        .colorScheme.background
                ),
        ) {
            Text(
                text = state.geocodedAddress,
                modifier = Modifier.padding(Spacing.sm),
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(
                modifier = Modifier.height(Spacing.sm),
            )
            JetMartButton(
                loading = state.loading,
                label = stringResource(R.string.confirm_address),

                onClick = {
                    onAction(
                        AddressLocationActions.OnSaveAddress(
                            location = state.choosedLocation,
                            geocodedLocation = state.geocodedAddress
                        )

                    )
                }
            )


        }


    }


    if (state.showLocationRational) {
        JetMartAlertDialog(
            positiveText = stringResource(R.string.enable),
            negativeText = stringResource(R.string.now_now),
            onDismiss = {
                onAction(AddressLocationActions.DismissLocationDialog)
            },
            onNegative = {
                onAction(AddressLocationActions.DismissLocationDialog)
            },
            onPositive = {
                permissionLauncher.requestLocations(context)
            },
            title = stringResource(R.string.location_required),
            subtitle = stringResource(R.string.explain_location),
        )
    }


}


private fun ActivityResultLauncher<Array<String>>.requestLocations(
    context: Context,
) {

    val hasLocation = context.isLocationPermissionsAllowed()
    val locationPermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
    )
    if (!hasLocation) {
        launch(locationPermissions)
    }


}


@Preview(showBackground = true)
@Composable
private fun PreviewAddressLcoationScreen() {

    JetMartTheme {
        AddressLocationScreen(
            onAction = {},
            state = AddressLocationState()
        )
    }
}