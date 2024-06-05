package com.mohaberabi.jetmart.features.order.domain.model

import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import java.time.Instant
import java.time.ZonedDateTime
import java.util.UUID


data class OrderModel(
    val id: String = UUID.randomUUID().toString(),
    val address: AddressModel,
    val items: List<OrderItemModel>,
    val user: UserModel,
    val paymentMethod: PaymentMethod,
    val orderTime: OrderTime,
    val createdAt: ZonedDateTime,
    val uid: String,
    val deliveryFees: Double,
    val status: OrderStatus = OrderStatus.SENT,
) {
    val cartTotal: Double
        get() = items.sumOf { it.totalPrice }
    val orderTotal: Double
        get() = cartTotal + deliveryFees
}

fun OrderModel.toOrderOverView(): OrderOverViewModel {
    return OrderOverViewModel(
        id = id,
        deliveryFees = deliveryFees,
        createdAt = createdAt,
        cartTotal = cartTotal,
        orderTime = orderTime,
        paymentMethod = paymentMethod,
        status = status,
    )
}