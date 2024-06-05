package com.mohaberabi.jetmart.core.domain.geocoder

import com.mohaberabi.jetmart.core.util.AppResult
import com.mohaberabi.jetmart.core.util.error.DataError

interface JetMartGeoCoder {


    suspend fun geocodeFromLatLng(
        lat: Double, lng: Double,
    ): AppResult<String, DataError>
}