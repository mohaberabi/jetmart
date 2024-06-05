package com.mohaberabi.jetmart.features.order.tracking.presenation.screen

import JetMartScaffold
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAlertDialog
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartError
import com.mohaberabi.jetmart.core.presentation.compose.JetMartLoader
import com.mohaberabi.jetmart.core.presentation.compose.LazyCachedImageRow
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.compose.PaymentSummary
import com.mohaberabi.jetmart.core.presentation.theme.HomeIcon
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.presentation.theme.TrackingIcon
import com.mohaberabi.jetmart.features.address.presentation.compose.AddressCard
import com.mohaberabi.jetmart.features.checkout.presentation.screen.testAddress
import com.mohaberabi.jetmart.features.order.domain.model.OrderStatus
import com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel.OrderTrackingActions
import com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel.OrderTrackingEvents
import com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel.OrderTrackingState
import com.mohaberabi.jetmart.features.order.tracking.presenation.viewmodel.OrderTrackingViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun OrderTrackingScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: OrderTrackingViewModel = koinViewModel(),
    onShowSnackBar: (String) -> Unit,
    onGoBack: () -> Unit,
) {


    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is OrderTrackingEvents.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    OrderTrackingScreen(
        state = state,
        modifier = modifier,
        onGoBack = onGoBack,
        onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderTrackingScreen(
    modifier: Modifier = Modifier,
    state: OrderTrackingState,
    onGoBack: () -> Unit,
    onAction: (OrderTrackingActions) -> Unit
) {

    var showCancelDialog by rememberSaveable {
        mutableStateOf(false)
    }
    JetMartScaffold(
        topAppBar = {
            JetMartAppBar(
                showBackButton = true,
                onBackClick = onGoBack,
                titleContent = {
                    if (state is OrderTrackingState.Done) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = Spacing.sm),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = state.order.id,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.secondary)
                            )
                            IconButton(
                                onClick = {},
                            ) {

                                Icon(
                                    imageVector = Icons.Default.MoreVert,
                                    contentDescription = stringResource(R.string.copy)
                                )
                            }
                        }

                    }
                },
                color = MaterialTheme.colorScheme.primary,
                navigationIconColor = MaterialTheme.colorScheme.secondary,
            )
        }
    ) {

            padding ->


        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (state) {
                is OrderTrackingState.Done -> {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = modifier
                            .padding(Spacing.lg)
                            .scrollable(
                                rememberScrollState(),
                                Orientation.Vertical
                            ),
                    ) {

                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Image(
                                imageVector = TrackingIcon,
                                modifier = Modifier
                                    .size(200.dp),
                                contentDescription = stringResource(R.string.order_is_tracking)
                            )
                        }

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                text = stringResource(id = state.order.status.title),
                                style = MaterialTheme.typography
                                    .headlineLarge.copy(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold
                                    )
                            )
                            Text(
                                text = stringResource(id = state.order.status.subtitle),
                                style = MaterialTheme.typography
                                    .bodyLarge
                            )

                        }
                        Spacer(modifier = Modifier.height(Spacing.md))

                        AddressCard(
                            address = testAddress,
                            isFavorite = true
                        )

                        Spacer(modifier = Modifier.height(Spacing.md))
                        Text(
                            text = stringResource(R.string.items_summary),
                            style = MaterialTheme.typography
                                .titleMedium.copy(
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                        )
                        LazyCachedImageRow(
                            images = state.order.items.map { it.image }
                        )
                        Spacer(modifier = Modifier.height(Spacing.md))

                        PaymentSummary(
                            cartTotal = state.order.cartTotal,
                            deliveryFees = state.order.deliveryFees,
                            orderTotal = state.order.orderTotal
                        )

                        if (state.order.status == OrderStatus.SENT) {
                            TextButton(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    showCancelDialog = true

                                },
                            ) {


                                Text(
                                    text = stringResource(id = R.string.cancel_order),
                                    style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray)
                                )
                            }
                        }

                    }
                }

                is OrderTrackingState.Error -> JetMartError(errorTitle = state.error)
                else -> JetMartLoader()
            }
        }


    }

    if (showCancelDialog) {
        JetMartAlertDialog(
            title = stringResource(R.string.cancel_order),
            onDismiss = { showCancelDialog = false },
            positiveText = stringResource(R.string.cancel),
            negativeText = stringResource(R.string.changed_my_mind),
            onNegative = { showCancelDialog = false },
            onPositive = {
                onAction(OrderTrackingActions.OnCancelOrder)
                showCancelDialog = false
            },
            subtitle = stringResource(R.string.are_you_sure_you_want_cancel_the_order_please_know_order_can_not_be_canceled_if_it_is_accepted)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewOrderTracking() {

    JetMartTheme {
        OrderTrackingScreen(
            onGoBack = {},
            onAction = {},
            state = OrderTrackingState.Loading,
        )
    }
}