package com.mohaberabi.jetmart.features.listing.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.CachedImage
import com.mohaberabi.jetmart.core.presentation.compose.LocalizedText
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.RemoveIcon
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.item.domain.model.ItemListingModel


@Composable
fun ListingItemCard(
    modifier: Modifier = Modifier,
    item: ItemListingModel,
    cartQty: Int = 0,
    onClick: () -> Unit
) {


    ElevatedCard(
        modifier = modifier
            .padding(Spacing.sm)
            .clickable {
                onClick()
            },
        shape = RoundedCornerShape(Spacing.sm),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {

        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(Spacing.sm)
        ) {
            CachedImage(
                model = item.image,
                borderRadius = 4.dp,
                size = 90.dp,
            )
            LocalizedText(
                dynamicText = item.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = stringResource(R.string.egp, item.price),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.primary)
            )
            Spacer(modifier = Modifier.height(Spacing.sm))

            Box(modifier = Modifier.clip(CircleShape)) {


            }

        }


    }
}


@Composable
fun AddButton(modifier: Modifier = Modifier) {


}

@Composable
fun QtyRow(
    modifier: Modifier = Modifier,
    qty: Int,
    onInc: () -> Unit,
    onDec: () -> Unit
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(Spacing.sm))
            .background(MaterialTheme.colorScheme.secondary),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End,
        ) {
            IconButton(
                onClick = onInc,
            ) {


                Icon(
                    imageVector = Icons.Default.Add,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Add"
                )
            }


            Text(
                text = "${qty}",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary
                ),
            )
            IconButton(
                onClick = onDec,
            ) {

                Icon(
                    imageVector = RemoveIcon,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(24.dp),
                    contentDescription = "Remove"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListingItemCardPreview() {
    JetMartTheme {
        ListingItemCard(
            onClick = {},
            item = ItemListingModel(
                "", "",
                "", "",
                mapOf(),
                200.0
            )
        )
    }
}