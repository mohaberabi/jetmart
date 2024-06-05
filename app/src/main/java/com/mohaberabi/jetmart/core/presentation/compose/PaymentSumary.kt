package com.mohaberabi.jetmart.core.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@Composable
fun PaymentSummary(
    modifier: Modifier = Modifier,

    cartTotal: Double,
    deliveryFees: Double,
    orderTotal: Double
) {

    Column(
        modifier = modifier,
    ) {


        Text(
            text = stringResource(R.string.payment_overview),
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        )
        PaymentRow(
            leading = R.string.cart_total,
            amount = cartTotal
        )
        PaymentRow(
            leading = R.string.del_fees,
            amount = deliveryFees,
        )
        PaymentRow(
            leading = R.string.order_ttl,
            amount = orderTotal,
        )
    }
}

@Composable
fun PaymentRow(
    @StringRes leading: Int,
    amount: Double
) {


    PairRow(
        leading = stringResource(
            leading,
            ""
        ), trailing = stringResource(
            id = R.string.egp,
            amount
        )
    )

}

@Composable
fun PairRow(
    leading: String,
    trailing: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = Spacing.sm),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = leading,
            style = MaterialTheme.typography
                .bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                )
        )
        Text(
            text = trailing,
            style = MaterialTheme.typography
                .bodyLarge.copy(
                )
        )
    }
}
