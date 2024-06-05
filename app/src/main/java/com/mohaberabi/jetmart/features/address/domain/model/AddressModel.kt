package com.mohaberabi.jetmart.features.address.domain.model

data class AddressModel(
    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val label: String,
    val location: String
)

data class PendingAddressModel(
    val userId: String,
    val address: AddressModel
)

data class DeletedAddressModel(
    val id: String,
    val userId: String
)