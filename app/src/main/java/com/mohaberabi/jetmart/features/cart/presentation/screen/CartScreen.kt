package com.mohaberabi.jetmart.features.cart.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartPlaceHolder
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.features.cart.presentation.compose.CartBottomBar
import com.mohaberabi.jetmart.features.cart.presentation.compose.CartItemCard
import com.mohaberabi.jetmart.features.cart.presentation.viewmodel.CartActions
import com.mohaberabi.jetmart.features.cart.presentation.viewmodel.CartEvents
import com.mohaberabi.jetmart.features.cart.presentation.viewmodel.CartState
import com.mohaberabi.jetmart.features.cart.presentation.viewmodel.CartViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CartScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = koinViewModel(),
    onGoConfirmOrder: () -> Unit,
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is CartEvents.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    CartScreen(
        state = state,
        modifier = modifier,
        onAction = { action ->
            when (action) {
                CartActions.OnBackClick -> onBackClick()
                CartActions.OnConfirmOrder -> onGoConfirmOrder()
                else -> viewModel.onAction(action)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    state: CartState,
    onAction: (CartActions) -> Unit
) {

    JetMartScaffold(
        modifier = modifier,
        topAppBar = {
            JetMartAppBar(
                actions = {
                    if (state.cart.items.isNotEmpty())
                        IconButton(
                            onClick = {
                                onAction(CartActions.OnClearCartClicked)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                tint = Color.Gray,
                                contentDescription = stringResource(R.string.clear_cart)
                            )
                        }
                },
                showBackButton = true,
                isCenter = false,
                color = MaterialTheme.colorScheme.primary,
                navigationIconColor = MaterialTheme.colorScheme.secondary,
                onBackClick = {
                    onAction(CartActions.OnBackClick)
                },
                titleContent = {
                    Text(
                        text = stringResource(R.string.cart),
                        style = MaterialTheme.typography.displaySmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold,
                        )
                    )
                }


            )
        },
        bottomAppBar = {
            CartBottomBar(
                cartTotal = state.cart.cartTotal,
                leading = R.string.confirm_order,
                onClick = {
                    onAction(CartActions.OnConfirmOrder)
                },
            )
        }
    ) {


            padding ->

        if (state.cart.items.isEmpty()) {
            JetMartPlaceHolder()
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding),
            ) {

                items(
                    state.cart.items.values.toList(),
                ) { item ->
                    CartItemCard(
                        item = item,
                        onDecrement = {
                            onAction(CartActions.OnDecrementQty(it))
                        },
                        onIncrement = {
                            onAction(CartActions.OnIncrementQty(it))
                        }
                    )
                }

            }
        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCartScreen() {


    JetMartTheme {
        CartScreen(
            state = CartState(),


            onAction = {},
        )
    }
}