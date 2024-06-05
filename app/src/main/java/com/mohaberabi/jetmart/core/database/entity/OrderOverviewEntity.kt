package com.mohaberabi.jetmart.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mohaberabi.jetmart.core.util.extensions.toEnum
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderStatus
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime


@Entity(tableName = "orders")
data class OrderOverviewEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val status: String,
    val deliveryFees: Double,
    val cartTotal: Double,
    val createdAtMillis: Long,
    val orderTime: String,
    val paymentMethod: String,
)

fun OrderOverViewModel.toOrderOverViewEntity(): OrderOverviewEntity {
    return OrderOverviewEntity(
        id = id,
        status = status.name,
        deliveryFees = deliveryFees,
        cartTotal = cartTotal,
        orderTime = orderTime.name,
        paymentMethod = paymentMethod.name,
        createdAtMillis = createdAt.toInstant().toEpochMilli(),
    )
}

fun OrderOverviewEntity.toOrderOverViewModel(): OrderOverViewModel {
    return OrderOverViewModel(
        id = id,
        status = status.toEnum<OrderStatus>() ?: OrderStatus.SENT,
        deliveryFees = deliveryFees,
        cartTotal = cartTotal,
        orderTime = orderTime.toEnum<OrderTime>() ?: OrderTime.ASAP,
        paymentMethod = paymentMethod.toEnum<PaymentMethod>() ?: PaymentMethod.COD,
        createdAt = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(createdAtMillis),
            ZoneId.systemDefault()
        ),
    )
}