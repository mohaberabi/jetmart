package com.mohaberabi.jetmart.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel


@Entity(tableName = "cart")
data class CartItemEntity(

    @PrimaryKey(autoGenerate = false)
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val image: String,
    val qty: Int,
    val price: Double,
    val didPriceChange: Boolean = false,
    val didBecameOutStock: Boolean = false
)

fun CartItemEntity.toCartItem(): CartItemModel {
    return CartItemModel(
        id = id,
        name = mapOf("en" to nameEn, "ar" to nameAr),
        qty = qty,
        image = image,
        price = price,
        didPriceChange = didPriceChange,
        didBecameOutStock = didBecameOutStock
    )
}

fun CartItemModel.toCartItemEntity(): CartItemEntity {
    return CartItemEntity(
        id = id,
        nameAr = name.getOrDefault("ar", ""),
        nameEn = name.getOrDefault("en", ""),
        qty = qty,
        image = image,
        price = price,
        didPriceChange = didPriceChange,
        didBecameOutStock = didBecameOutStock
    )
}