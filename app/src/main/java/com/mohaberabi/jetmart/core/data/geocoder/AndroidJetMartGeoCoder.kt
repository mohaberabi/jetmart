package com.mohaberabi.jetmart.core.data.geocoder

import android.content.Context
import android.location.Geocoder
import androidx.datastore.core.IOException
import com.mohaberabi.jetmart.core.domain.geocoder.JetMartGeoCoder
import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.DispatchersProvider
import com.mohaberabi.jetmart.core.util.error.DataError
import kotlinx.coroutines.withContext

class AndroidJetMartGeoCoder(
    private val dispatchers: DispatchersProvider,
    private val context: Context,
) : JetMartGeoCoder {

    private val geocoder = Geocoder(context)

    @Suppress("DEPRECATION")
    override suspend fun geocodeFromLatLng(
        lat: Double, lng: Double,
    ): AppResult<String, DataError> {
        return withContext(dispatchers.io) {

            try {
                val results = geocoder.getFromLocation(lat, lng, 1)
                return@withContext if (results.isNullOrEmpty()) {
                    AppResult.Error(DataError.Local.UNKNOWN)
                } else {
                    val address = results[0]
                    val formattedAddress = buildString {
                        append(address.subAdminArea + ",")
                        append(address.locality + ",")
                        append(address.adminArea + ",")
                        append(address.countryName + ",")
                    }
                    AppResult.Done(formattedAddress)
                }

            } catch (e: IOException) {
                e.printStackTrace()
                AppResult.Error(DataError.Local.IOException)
            }

        }


    }
}