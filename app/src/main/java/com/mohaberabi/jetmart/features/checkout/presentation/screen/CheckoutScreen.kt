package com.mohaberabi.jetmart.features.checkout.presentation.screen

import JetMartScaffold
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.CachedImage
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.compose.JetMartRadio
import com.mohaberabi.jetmart.core.presentation.compose.LazyCachedImageRow
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.compose.PaymentSummary
import com.mohaberabi.jetmart.core.presentation.compose.SimpleListItem
import com.mohaberabi.jetmart.core.presentation.theme.CartIcon
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel
import com.mohaberabi.jetmart.features.address.presentation.compose.AddressCard
import com.mohaberabi.jetmart.features.checkout.model.OrderTime
import com.mohaberabi.jetmart.features.checkout.model.PaymentMethod
import com.mohaberabi.jetmart.features.checkout.presentation.viewmodel.CheckoutActions
import com.mohaberabi.jetmart.features.checkout.presentation.viewmodel.CheckoutEvents
import com.mohaberabi.jetmart.features.checkout.presentation.viewmodel.CheckoutState
import com.mohaberabi.jetmart.features.checkout.presentation.viewmodel.CheckoutViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun CheckoutScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: CheckoutViewModel = koinViewModel(),
    onShowSnackBar: (String) -> Unit,
    onGoOrderTracking: (String) -> Unit
) {


    val context = LocalContext.current
    ObserveAsEvent(
        flow = viewModel.event,
    ) { event ->
        when (event) {
            is CheckoutEvents.Error -> onShowSnackBar(event.error.asString(context))
            is CheckoutEvents.OrderCreated -> onGoOrderTracking(event.id)
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CheckoutScreen(
        state = state,
        modifier = modifier,
        onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    state: CheckoutState,
    onAction: (CheckoutActions) -> Unit,
) {
    JetMartScaffold(
        modifier = modifier,
        topAppBar = {
            JetMartAppBar(
                showBackButton = true,
                isCenter = false,
                titleContent = {

                    Text(
                        text = stringResource(R.string.checkout),
                        style = MaterialTheme.typography.headlineLarge
                            .copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                    )
                },

                titleColor = MaterialTheme.colorScheme.secondary,
                color = MaterialTheme.colorScheme.primary,
                navigationIconColor = MaterialTheme.colorScheme.secondary
            )
        }
    ) {


            padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .scrollable(rememberScrollState(), Orientation.Vertical)
        ) {
            Surface(

                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 0.dp,
                shadowElevation = 0.dp,
                shape = RoundedCornerShape(
                    bottomEnd = Spacing.lg,
                    bottomStart = Spacing.lg
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(Spacing.sm)
                        .wrapContentHeight()
                ) {
                    Text(
                        text = stringResource(
                            R.string.cart_count,
                            state.cart.items.size
                        ),
                        style = MaterialTheme.typography
                            .titleLarge.copy(color = MaterialTheme.colorScheme.onPrimary)
                    )


                    LazyCachedImageRow(
                        images = state.cart.items.values.map { it.image },
                    )
                }


            }

            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(Spacing.md)
            ) {

                state.choosedAddress?.let {
                    AddressCard(
                        address = state.choosedAddress,
                        isFavorite = true
                    )

                }

                Spacer(
                    modifier = Modifier
                        .height(Spacing.md),
                )
                JetMartRadio(
                    items = PaymentMethod.entries,
                    current = state.paymentMethod,
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.choose_payment_method),
                    labelContent = {
                        Text(
                            text = stringResource(id = it!!.title),
                        )
                    }
                )
                JetMartRadio(
                    items = OrderTime.entries,
                    current = state.orderTime,
                    modifier = Modifier.fillMaxWidth(),
                    title = stringResource(R.string.choose_delivery_time),
                    labelContent = {
                        Text(
                            text = stringResource(id = it!!.title),
                        )
                    }
                )
                Spacer(modifier = Modifier.height(Spacing.lg))

                PaymentSummary(
                    cartTotal = state.cartTotal,
                    deliveryFees = state.deliveryFees,
                    orderTotal = state.orderTotal
                )
                JetMartButton(
                    loading = state.loading,
                    label = stringResource(
                        id = R.string.egp,
                        stringResource(
                            id = R.string.confirm_order,
                            state.orderTotal
                        )
                    ),
                    onClick = {
                        onAction(CheckoutActions.OnConfirmOrder)
                    },
                )


            }


        }

    }

}


val testAddress = AddressModel(
    "", 0.0, 0.0,
    "Hay Awal Mant2a 6 villa 101",
    "Office ",
    "New Cairo"
)

@Preview(showBackground = true)
@Composable
private fun CheckoutScreenPreview() {
    JetMartTheme {

        CheckoutScreen(
            state = CheckoutState(),
            onAction = {}
        )

    }
}