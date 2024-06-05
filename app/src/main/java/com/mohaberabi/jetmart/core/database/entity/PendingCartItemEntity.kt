package com.mohaberabi.jetmart.core.database.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jetmart.features.cart.domain.model.PendingCartItem


@Entity("pendingCartItem")
data class PendingCartItemEntity(
    @Embedded val item: CartItemEntity,
    val userId: String,
    @PrimaryKey(autoGenerate = false)
    val itemId: String
)


fun PendingCartItemEntity.toPendingCartItem(): PendingCartItem {
    return PendingCartItem(
        item = item.toCartItem(),
        userId = userId,
    )
}

fun PendingCartItem.toPendingCartItemEntity(): PendingCartItemEntity {
    return PendingCartItemEntity(
        item = item.toCartItemEntity(),
        userId = userId,
        itemId = item.id
    )
}