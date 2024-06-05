package com.mohaberabi.jetmart.core.data.locaiton

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.domain.location.JetMartLocationProvider
import com.mohaberabi.jetmart.core.domain.location.JetMartLocation
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AndroidJetMartLocationProvider(
    private val context: Context,
    private val dispatchersProvider: DispatchersProvider
) : JetMartLocationProvider {


    private val client: FusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }


    @Suppress("MissingPermission")
    override fun listenToLocation(): Flow<JetMartLocation> {

        val locationRequest = LocationRequest.Builder(
            LOCATION_PRIORITY,
            TimeUnit.MINUTES.toMillis(CHECKING_INTERVAl),
        ).setMinUpdateDistanceMeters(MIN_DISTANCE).build()


        return callbackFlow {

            if (!isLocationPermisisonAllowed()) {
                close()
                return@callbackFlow
            } else {

                val locationCallBack = object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        locationResult.lastLocation?.let { lastLocation ->
                            val jetMartLocation = JetMartLocation(
                                lat = lastLocation.latitude,
                                lng = lastLocation.longitude
                            )
                            launch {
                                trySend(jetMartLocation)
                            }
                        }
                    }
                }
                client.requestLocationUpdates(
                    locationRequest,
                    locationCallBack,
                    Looper.getMainLooper()
                )
                awaitClose {
                    client.removeLocationUpdates(locationCallBack)
                }
            }

        }.flowOn(dispatchersProvider.io)


    }

    override fun isLocationPermisisonAllowed(): Boolean {
        val coarse = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val fine = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return coarse && fine
    }

    @Suppress("MissingPermission")
    override suspend fun getCurrentLocation(): JetMartLocation? {
        val location = client.lastLocation.await()
        return if (location == null) {
            null
        } else {
            JetMartLocation(
                lat = location.latitude,
                lng = location.longitude
            )
        }
    }

    companion object {
        const val LOCATION_PRIORITY = Priority.PRIORITY_BALANCED_POWER_ACCURACY

        // update every 10 minutes
        const val CHECKING_INTERVAl = 10L

        // if more than 1 km from last checkpoint location
        const val MIN_DISTANCE = 1000f
    }
}