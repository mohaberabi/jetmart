package com.mohaberabi.jetmart.features.order.data.source.remote.dto

import com.mohaberabi.jetmart.core.util.extensions.toEnum
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderStatus
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class OrderOverViewDto(
    val id: String,
    val status: String,
    val deliveryFees: Double,
    val cartTotal: Double,
    val createdAtMillis: Long,
    val orderTime: String,
    val paymentMethod: String,
) {
    constructor() : this(
        id = "",
        status = "",
        deliveryFees = 0.0,
        cartTotal = 0.0,
        createdAtMillis = 0L,
        orderTime = "",
        paymentMethod = ""
    )
}


fun OrderOverViewDto.toOrderOverView(): OrderOverViewModel {
    return OrderOverViewModel(
        id = id,
        status = status.toEnum<OrderStatus>() ?: OrderStatus.SENT,
        orderTime = orderTime.toEnum<OrderTime>() ?: OrderTime.ASAP,
        cartTotal = cartTotal,
        createdAt = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(createdAtMillis),
            ZoneId.systemDefault()
        ),
        deliveryFees = deliveryFees,
        paymentMethod = paymentMethod.toEnum<PaymentMethod>() ?: PaymentMethod.COD
    )
}