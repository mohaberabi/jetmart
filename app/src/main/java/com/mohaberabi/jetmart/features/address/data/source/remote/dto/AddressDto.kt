package com.mohaberabi.jetmart.features.address.data.source.remote.dto

import com.mohaberabi.jetmart.features.address.domain.model.AddressModel

data class AddressDto(

    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val label: String,
    val location: String
) {
    constructor() : this("", 0.0, 0.0, "", "", "")
}

fun AddressModel.toAddressDto(): AddressDto {
    return AddressDto(
        id = id,
        lat = lat,
        lng = lng,
        address = address,
        label = label,
        location = location
    )
}


fun AddressDto.toAddressModel(): AddressModel {
    return AddressModel(
        id = id,
        lat = lat,
        lng = lng,
        address = address,
        label = label,
        location = location
    )
}
