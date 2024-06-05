package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.LocationIcon
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.const.AppConst
import com.mohaberabi.jetmart.core.domain.location.JetMartLocation
import kotlinx.coroutines.launch


@Composable
fun GoogleMapComposable(
    modifier: Modifier = Modifier,
    onLocationChanged: (JetMartLocation) -> Unit,
    currentLocation: JetMartLocation?,
    showMyLocationButton: Boolean = true,
    onMyLocationButtonClicked: () -> Unit = {},
) {
    val markerState = rememberMarkerState()
    val cameraPositionState = rememberCameraPositionState()
    val currentLocationScope = rememberCoroutineScope()
    var location = remember {
        LatLng(
            currentLocation?.lat ?: AppConst.defaultAppLocation.lat,
            currentLocation?.lng ?: AppConst.defaultAppLocation.lng
        )
    }


    LaunchedEffect(
        key1 = Unit,
    ) {
        animateToLocation(
            cameraPositionState = cameraPositionState,
            newLocation = location
        )

        markerState.position = location
        onLocationChanged(JetMartLocation(location.latitude,location.longitude))
    }

    Box(
        contentAlignment = Alignment.CenterEnd,
    ) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = modifier,

            uiSettings = MapUiSettings(
                zoomGesturesEnabled = true,
            ),
            onMapClick = { latlng ->
                location = latlng
                onLocationChanged(
                    JetMartLocation(
                        lat = latlng.latitude,
                        lng = latlng.longitude
                    )
                )
                markerState.position = latlng
            }
        ) {


            MarkerComposable(
                location,
                state = markerState
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(35.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primary),
                ) {
                    Icon(
                        imageVector = LocationIcon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }


        if (showMyLocationButton) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.background)
                    .padding(Spacing.sm)
            ) {
                IconButton(
                    onClick = {
                        if (currentLocation != null) {
                            currentLocationScope.launch {
                                val latLng = LatLng(
                                    currentLocation.lat,
                                    currentLocation.lng
                                )
                                animateToLocation(
                                    cameraPositionState = cameraPositionState,
                                    newLocation = latLng
                                )
                                markerState.position = latLng
                                location = latLng
                            }

                        } else {
                            onMyLocationButtonClicked()
                        }

                    },
                ) {
                    Icon(
                        imageVector = LocationIcon,
                        modifier = Modifier,
                        contentDescription = stringResource(R.string.location),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            }
        }


    }


}

private suspend fun animateToLocation(
    cameraPositionState: CameraPositionState,
    newLocation: LatLng,
    zoomLevel: Float = 17f
) {
    cameraPositionState.animate(
        CameraUpdateFactory.newLatLngZoom(
            newLocation,
            zoomLevel
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewGoogleMaps() {
    JetMartTheme {
        GoogleMapComposable(
            currentLocation = JetMartLocation(
                0.0,
                0.0
            ),
            onLocationChanged = {},
        )
    }
}