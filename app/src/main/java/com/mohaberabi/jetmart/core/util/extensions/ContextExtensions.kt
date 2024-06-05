package com.mohaberabi.jetmart.core.util.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings


fun Context.openAppSettings(
    action: String = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
) {
    val intent = Intent(action)
    intent.data = Uri.fromParts("package", packageName, null)
    startActivity(intent)
}