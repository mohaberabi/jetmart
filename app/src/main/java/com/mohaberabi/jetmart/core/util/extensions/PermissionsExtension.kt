package com.mohaberabi.jetmart.core.util.extensions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.core.content.ContextCompat


fun Context.hasPermissionAllowed(
    permission: String,
): Boolean = ContextCompat.checkSelfPermission(
    this,
    permission
) == PackageManager.PERMISSION_GRANTED

fun Context.isLocationPermissionsAllowed(): Boolean {
    val coarse = hasPermissionAllowed(Manifest.permission.ACCESS_COARSE_LOCATION)
    val fine = hasPermissionAllowed(Manifest.permission.ACCESS_FINE_LOCATION)
    return fine && coarse
}

fun ComponentActivity.shouldShowPermissionRationale(
    permission: String,
): Boolean =
    shouldShowRequestPermissionRationale(permission)

fun ComponentActivity.shouldShowLocationPermissionRationale(): Boolean =
    shouldShowPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION) ||
            shouldShowPermissionRationale(
                Manifest.permission.ACCESS_COARSE_LOCATION
            )


fun needsNotificationsPermissions(): Boolean = Build.VERSION.SDK_INT >= 33
