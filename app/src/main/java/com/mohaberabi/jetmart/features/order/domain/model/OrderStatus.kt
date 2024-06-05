package com.mohaberabi.jetmart.features.order.domain.model

import androidx.annotation.StringRes
import com.mohaberabi.jetmart.R

enum class OrderStatus(
    @StringRes val title: Int,
    @StringRes val subtitle: Int
) {

    SENT(
        title = R.string.order_sent_ttl,
        subtitle = R.string.order_sent_subttl
    ),
    PREPARING(
        title = R.string.order_receved_ttl,
        subtitle = R.string.order_reced_subttl
    ),
    DONE(
        title = R.string.order_done_ttl,
        subtitle = R.string.order_done_subttl
    ),
    CANCELED(
        title = R.string.order_cancel_ttlt,
        subtitle = R.string.order_cacnel_subttl
    )

}