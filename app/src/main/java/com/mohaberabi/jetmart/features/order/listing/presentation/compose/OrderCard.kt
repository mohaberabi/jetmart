package com.mohaberabi.jetmart.features.order.listing.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.SimpleListItem
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.MotoIcon
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.extensions.appDefaultFormat
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.order.domain.model.OrderOverViewModel
import com.mohaberabi.jetmart.features.order.domain.model.OrderStatus
import java.time.ZonedDateTime


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderCard(
    modifier: Modifier = Modifier,
    order: OrderOverViewModel,
    onClick: () -> Unit
) {


    ElevatedCard(
        modifier = modifier
            .fillMaxWidth(),
        onClick = onClick
    ) {

        Column(
            modifier = Modifier.padding(Spacing.sm),
        ) {

            Row(
                modifier = Modifier
                    .padding(Spacing.sm)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = "#${order.id}",

                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                    ), maxLines = 1,
                    overflow = TextOverflow.Ellipsis

                    )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Spacing.sm))
                        .background(
                            MaterialTheme.colorScheme.primary,
                        )
                ) {
                    Text(
                        text = stringResource(id = order.status.title),
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                        ),
                        modifier = Modifier.padding(Spacing.sm)
                    )
                }
            }

            Text(
                text = order.createdAt.appDefaultFormat(),
                style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray)
            )


            Spacer(modifier = Modifier.height(Spacing.sm))

            SummaryRow(
                title = stringResource(id = R.string.order_ttl),
                amount = order.totalAmount
            )
            Spacer(modifier = Modifier.height(Spacing.sm))

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {


                Text(
                    text = stringResource(id = order.paymentMethod.title),

                    style = MaterialTheme.typography.bodySmall,
                )
                Text(
                    text = stringResource(id = order.orderTime.title),

                    style = MaterialTheme.typography.bodySmall,
                )


            }


        }

    }


}


@Composable
private fun SummaryRow(
    modifier: Modifier = Modifier,
    title: String,
    amount: Any
) {

    Row(
        modifier = modifier.padding(end = Spacing.xs),
        verticalAlignment = Alignment.CenterVertically,

        ) {

        Text(
            text = "${title} :",
            style = MaterialTheme.typography.bodySmall
        )
        Text(
            text = stringResource(id = R.string.egp, amount),
            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
        )
    }
}


@Preview
@Composable
private fun PreviewOrderCard() {

    JetMartTheme {
        OrderCard(
            onClick = {},
            order = OrderOverViewModel(
                "1231232132132130029",
                OrderStatus.DONE,
                25.0,
                200.0,
                createdAt = ZonedDateTime.now(),
                orderTime = OrderTime.ASAP,
                paymentMethod = PaymentMethod.COD
            )
        )
    }

}