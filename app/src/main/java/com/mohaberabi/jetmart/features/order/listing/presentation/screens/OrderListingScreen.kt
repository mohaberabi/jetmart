package com.mohaberabi.jetmart.features.order.listing.presentation.screens

import JetMartScaffold
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartError
import com.mohaberabi.jetmart.core.presentation.compose.JetMartLoader
import com.mohaberabi.jetmart.core.presentation.compose.JetMartPullToRefresh
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.order.listing.presentation.compose.OrderCard
import com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel.OrderListingActions
import com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel.OrderListingState
import com.mohaberabi.jetmart.features.order.listing.presentation.viewmodel.OrderListingViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun OrderListingScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: OrderListingViewModel = koinViewModel(),

    onGoTrackOrder: (String) -> Unit,
    onGoBack: () -> Unit,
) {


    val state by viewModel.state.collectAsStateWithLifecycle()
    OrderListingScreen(
        state = state,
        modifier = modifier,
        onGoBack = onGoBack,
        onAction = { action ->
            if (action is OrderListingActions.OnOrderClick) {
                onGoTrackOrder(action.id)
            } else {
                viewModel.onAction(action)
            }
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderListingScreen(
    modifier: Modifier = Modifier,
    state: OrderListingState,
    onAction: (OrderListingActions) -> Unit,
    onGoBack: () -> Unit

) {


    JetMartScaffold(
        modifier = modifier,
        topAppBar = {
            JetMartAppBar(
                showBackButton = true,
                onBackClick = onGoBack,
                title = stringResource(id = R.string.orders),
                isCenter = true
            )
        },
    ) {

            padding ->


        JetMartPullToRefresh(
            onRefresh = {
                onAction(OrderListingActions.OnRefresh)
            },
            modifier = Modifier.padding(padding)
        ) { listState ->
            Box {
                when (state) {
                    is OrderListingState.Done -> {
                        LazyColumn(
                            state = listState,
                        ) {
                            items(state.orders) { order ->
                                OrderCard(
                                    order = order,
                                    modifier = Modifier.padding(Spacing.sm),
                                    onClick = { onAction(OrderListingActions.OnOrderClick(order.id)) },
                                )
                            }
                        }

                    }

                    is OrderListingState.Error -> JetMartError(errorTitle = state.error)
                    else -> JetMartLoader()
                }
            }
        }


    }
}