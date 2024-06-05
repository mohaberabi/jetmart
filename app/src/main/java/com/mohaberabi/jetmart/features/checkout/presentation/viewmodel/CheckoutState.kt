package com.mohaberabi.jetmart.features.checkout.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.const.AppConst
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.cart.domain.model.CartModel
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod

data class CheckoutState(


    val paymentMethod: PaymentMethod = PaymentMethod.COD,
    val orderTime: OrderTime = OrderTime.ASAP,
    val cart: CartModel = CartModel(),
    val choosedAddress: AddressModel? = null,
    val loading: Boolean = false

) {
    val deliveryFees: Double
        get() = AppConst.TEMP_DELIVERY_FEES
    val cartTotal: Double
        get() = cart.cartTotal
    val orderTotal: Double
        get() = deliveryFees + cart.cartTotal
}
