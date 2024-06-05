package com.mohaberabi.jetmart.features.cart.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.CachedImage
import com.mohaberabi.jetmart.core.presentation.compose.LocalizedText
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.item.presentation.screen.ItemQtySizes
import com.mohaberabi.jetmart.features.item.presentation.screen.JetMartItemQty


@Composable
fun CartItemCard(
    modifier: Modifier = Modifier,
    item: CartItemModel,
    onIncrement: (CartItemModel) -> Unit,
    onDecrement: (CartItemModel) -> Unit,

    ) {


    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,

        ) {


        CachedImage(
            model = item.image,
            size = 80.dp
        )



        Column {
            LocalizedText(
                dynamicText = item.name,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.egp, item.totalPrice),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )


                JetMartItemQty(
                    size = ItemQtySizes.SMALL,
                    onIncrement = { onIncrement(item) },
                    onDecrement = { onDecrement(item) },
                    qty = item.qty,
                    modifier = Modifier
                        .width(90.dp)
                        .height(30.dp)
                        .padding(end = Spacing.sm)
                )

            }

        }

    }
}


@Preview(
    showBackground = true,
)
@Composable
private fun CartItemCardPreview() {


    JetMartTheme {
        CartItemCard(
            onDecrement = {}, onIncrement = {},
            item = CartItemModel(
                "",
                99,
                290.0,
                "",
                mapOf(),
                false ,
            )
        )
    }
}