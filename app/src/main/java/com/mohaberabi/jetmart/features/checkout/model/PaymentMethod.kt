package com.mohaberabi.jetmart.features.checkout.model

import androidx.annotation.StringRes
import com.mohaberabi.jetmart.R

enum class PaymentMethod(
    @StringRes val title: Int,
    val id: String,
) {

    COD(
        R.string.cod,
        "cod"
    )
}

enum class OrderTime(
    @StringRes val title: Int,
    val id: String,
) {
    ASAP(
        title = R.string.asap,
        "asap"
    )

}