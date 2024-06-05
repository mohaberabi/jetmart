package com.mohaberabi.jetmart.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jetmart.features.address.domain.model.DeletedAddressModel
import com.mohaberabi.jetmart.features.address.domain.model.PendingAddressModel


@Entity(tableName = "pendingAddress")
data class PendingAddressEntity(
    @PrimaryKey(autoGenerate = false)
    val addressId: String,
    @Embedded
    val address: AddressEntity,
    val userId: String
)

@Entity("deletedAddress")
data class DeletedAddressEntity(
    @PrimaryKey(autoGenerate = false)
    val addressId: String,
    val userId: String,
)

fun DeletedAddressModel.toDeletedAddressEntity(): DeletedAddressEntity {
    return DeletedAddressEntity(
        addressId = id,
        userId = userId
    )
}

fun DeletedAddressEntity.toDeletedAddress(): DeletedAddressModel {
    return DeletedAddressModel(
        id = addressId,
        userId = userId
    )
}

fun PendingAddressModel.toPendingAddressEntity(): PendingAddressEntity {
    return PendingAddressEntity(
        address = address.toAddressEntity(),
        userId = userId,
        addressId = address.id
    )
}

fun PendingAddressEntity.toPendingAddress(): PendingAddressModel {
    return PendingAddressModel(
        address = address.toAddressModel(),
        userId = userId
    )
}