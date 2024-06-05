package com.mohaberabi.jetmart.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jetmart.features.cart.domain.model.DeletedCartItem


@Entity("deleteCartItem")
data class DeleteCartEntity(
    @PrimaryKey(autoGenerate = false)
    val cartItemId: String,
    val userId: String
)


fun DeleteCartEntity.toDeletedCartItem(): DeletedCartItem {
    return DeletedCartItem(
        cartItemId = cartItemId,
        userId = userId
    )
}

fun DeletedCartItem.toDeletedCartItemEntity(): DeleteCartEntity {
    return DeleteCartEntity(
        cartItemId = cartItemId,
        userId = userId
    )
}