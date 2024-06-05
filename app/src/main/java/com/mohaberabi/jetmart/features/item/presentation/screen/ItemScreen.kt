package com.mohaberabi.jetmart.features.item.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.BadgeButton
import com.mohaberabi.jetmart.core.presentation.compose.CachedImage
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomSheet
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.compose.JetMartLoader
import com.mohaberabi.jetmart.core.presentation.compose.JetMartPlaceHolder
import com.mohaberabi.jetmart.core.presentation.compose.LocalizedText
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.RemoveIcon
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.cart.domain.model.CartItemModel
import com.mohaberabi.jetmart.features.item.domain.model.ItemModel
import com.mohaberabi.jetmart.features.item.presentation.viewmodel.ItemActions
import com.mohaberabi.jetmart.features.item.presentation.viewmodel.ItemEvents
import com.mohaberabi.jetmart.features.item.presentation.viewmodel.ItemState
import com.mohaberabi.jetmart.features.item.presentation.viewmodel.ItemViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ItemScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: ItemViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onGoCart: () -> Unit,
    onGoSignIn: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {

    val context = LocalContext.current

    ObserveAsEvent(flow = viewModel.event) { event ->

        when (event) {
            is ItemEvents.Error -> onShowSnackBar(event.error.asString(context))
            ItemEvents.GoSignIn -> onGoSignIn()
        }
    }
    val itemState by viewModel.itemState.collectAsStateWithLifecycle()
    ItemScreen(
        itemState = itemState,
        modifier = modifier,
        onAction = { action ->
            if (action is ItemActions.OnCartClick) {
                onGoCart()
            } else {

                viewModel.onAction(action)
            }
        },
        onBackClick = onBackClick
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(
    modifier: Modifier = Modifier,
    itemState: ItemState,
    onBackClick: () -> Unit = {},
    onAction: (ItemActions) -> Unit
) {

    JetMartScaffold(
        topAppBar = {
            JetMartAppBar(
                actions = {
                    if (itemState.cartSize > 0)
                        BadgeButton(
                            label = "${itemState.cartSize}",
                            onClick = {
                                onAction(ItemActions.OnCartClick)
                            }
                        )
                },
                showBackButton = true,
                onBackClick = onBackClick
            )
        },
    ) { padding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(padding)
                .padding(Spacing.lg)
                .scrollable(
                    rememberScrollState(),
                    Orientation.Vertical
                )

        ) {
            if (itemState.loading) {
                JetMartLoader()
            } else {
                itemState.item?.let { item ->

                    CachedImage(
                        model = item.image,
                        size = 200.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                    LocalizedText(
                        dynamicText = item.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))

                    LocalizedText(
                        dynamicText = item.description,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(Spacing.md))
                    Text(
                        text = stringResource(R.string.egp, item.price),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    )

                    Spacer(modifier = Modifier.height(Spacing.sm))
                    itemState.cartItem?.let {

                        JetMartItemQty(
                            modifier = Modifier.fillMaxWidth(),
                            onIncrement = { onAction(ItemActions.OnIncQty) },
                            qty = it.qty,
                            onDecrement = {
                                onAction(ItemActions.OnDecQty)
                            },
                        )
                    } ?: JetMartButton(
                        loading = itemState.loadingCart,
                        onClick = { onAction(ItemActions.OnAddToCart) },
                        label = stringResource(R.string.add_to_cart)
                    )


                } ?: JetMartPlaceHolder()

            }
        }
    }


}


enum class ItemQtySizes(val size: Dp) {
    SMALL(20.dp),
    MEDIUM(26.dp),
    LARGE(56.dp),
    DEFAULT(40.dp)
}

@Composable
fun JetMartItemQty(
    modifier: Modifier = Modifier,
    qty: Int = 0,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
    size: ItemQtySizes = ItemQtySizes.DEFAULT
) {

    val typo = when (size) {
        ItemQtySizes.SMALL -> MaterialTheme.typography.bodyMedium
        ItemQtySizes.MEDIUM -> MaterialTheme.typography.titleLarge
        ItemQtySizes.LARGE -> MaterialTheme.typography.headlineSmall
        ItemQtySizes.DEFAULT -> MaterialTheme.typography.headlineLarge
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround,

        modifier = modifier
            .clip(
                RoundedCornerShape(Spacing.sm)
            )
            .background(MaterialTheme.colorScheme.secondary)

    ) {
        Icon(
            imageVector = RemoveIcon,
            contentDescription = "remove",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(size.size)
                .clickable {
                    onDecrement()
                }
        )
        Text(
            text = "${qty}",
            style = typo.copy(color = MaterialTheme.colorScheme.primary)
        )
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "add",
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .size(size.size)
                .clickable { onIncrement() }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewItemSheet() {

    JetMartTheme {

        ItemScreen(
            onAction = {},
            itemState = ItemState()
        )
    }
}
