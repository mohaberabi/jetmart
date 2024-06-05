package com.mohaberabi.jetmart.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel

@Entity("address")
data class AddressEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val lat: Double,
    val lng: Double,
    val address: String,
    val label: String,
    val location: String
)

fun AddressEntity.toAddressModel(): AddressModel {
    return AddressModel(
        id = id,
        lat = lat,
        lng = lng,
        address = address,
        label = label,
        location = location
    )
}

fun AddressModel.toAddressEntity(): AddressEntity {
    return AddressEntity(
        id = id,
        lat = lat,
        lng = lng,
        address = address,
        label = label,
        location = location
    )
}
