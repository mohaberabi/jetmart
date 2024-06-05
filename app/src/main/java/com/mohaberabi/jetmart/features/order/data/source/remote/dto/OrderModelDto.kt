package com.mohaberabi.jetmart.features.order.data.source.remote.dto

import com.mohaberabi.jetmart.core.util.extensions.toEnum
import com.mohaberabi.jetmart.features.address.data.source.remote.dto.AddressDto
import com.mohaberabi.jetmart.features.address.data.source.remote.dto.toAddressDto
import com.mohaberabi.jetmart.features.address.data.source.remote.dto.toAddressModel
import com.mohaberabi.jetmart.features.auth.data.source.remote.dto.UserDto
import com.mohaberabi.jetmart.features.auth.data.source.remote.dto.toUserModel
import com.mohaberabi.jetmart.features.auth.data.source.remote.dto.toUserModelDto
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.order.domain.model.OrderItemModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderStatus
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime

data class OrderModelDto(
    val id: String,
    val address: AddressDto,
    val items: List<OrderItemModelDto>,
    val user: UserDto,
    val paymentMethod: String,
    val orderTime: String,
    val createdAtMillis: Long,
    val uid: String,
    val deliveryFees: Double,
    val status: String,
) {
    constructor() : this(
        id = "",
        address = AddressDto(),
        items = listOf(),
        user = UserDto(),
        paymentMethod = "",
        orderTime = "",
        createdAtMillis = 0L,
        uid = "",
        deliveryFees = 0.0,
        status = ""
    )

    val cartTotal: Double
        get() = items.sumOf { it.totalPrice }
}

fun OrderModel.toOrderDto(): OrderModelDto {
    return OrderModelDto(
        id = id,
        address = address.toAddressDto(),
        items = items.map { it.toOrderItemModelDto() },
        user = user.toUserModelDto(),
        paymentMethod = paymentMethod.name,
        orderTime = orderTime.name,
        uid = uid,
        status = status.name,
        createdAtMillis = createdAt.toInstant().toEpochMilli(),
        deliveryFees = deliveryFees,
    )
}

fun OrderModelDto.toOrderOverView(): OrderOverViewModel {
    return OrderOverViewModel(
        id = id,
        status = status.toEnum<OrderStatus>() ?: OrderStatus.SENT,
        paymentMethod = paymentMethod.toEnum<PaymentMethod>() ?: PaymentMethod.COD,
        createdAt = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(createdAtMillis),
            ZoneId.systemDefault()
        ),
        deliveryFees = deliveryFees,
        orderTime = orderTime.toEnum<OrderTime>() ?: OrderTime.ASAP,
        cartTotal = cartTotal,
    )
}

fun OrderModelDto.toOrderModel(): OrderModel {
    return OrderModel(
        id = id,
        uid = uid,
        user = user.toUserModel(),
        items = items.map { it.toOrderItemModel() },
        status = status.toEnum<OrderStatus>() ?: OrderStatus.SENT,
        paymentMethod = paymentMethod.toEnum<PaymentMethod>() ?: PaymentMethod.COD,
        createdAt = ZonedDateTime.ofInstant(
            Instant.ofEpochMilli(createdAtMillis),
            ZoneId.systemDefault()
        ),
        address = address.toAddressModel(),
        deliveryFees = deliveryFees,
        orderTime = orderTime.toEnum<OrderTime>() ?: OrderTime.ASAP
    )
}