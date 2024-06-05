package com.mohaberabi.jetmart.features.order.domain.model

import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import java.time.ZonedDateTime

data class OrderOverViewModel(
    val id: String,
    val status: OrderStatus,
    val deliveryFees: Double,
    val cartTotal: Double,
    val createdAt: ZonedDateTime,
    val orderTime: OrderTime,
    val paymentMethod: PaymentMethod,
) {
    val totalAmount: Double
        get() = cartTotal + deliveryFees
}
