package com.mohaberabi.jetmart.features.address.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingActions
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingState
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddressPickerDialogRoot(
    modifier: Modifier = Modifier,
    viewModel: AddressListingViewModel = koinViewModel(),
    onDismiss: () -> Unit,
    onAddAddress: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AddressPickerDialog(
        state = state,
        onAddAddress = onAddAddress,
        modifier = modifier,
        onDismiss = onDismiss,
        onAction = viewModel::onAction
    )
}


@Composable
fun AddressPickerDialog(
    modifier: Modifier = Modifier,
    state: AddressListingState,
    onAddAddress: () -> Unit,
    onDismiss: () -> Unit,
    onAction: (AddressListingActions) -> Unit = {}
) {

    Dialog(
        properties = DialogProperties(),

        onDismissRequest = {
            onDismiss()
        },
    ) {

        Box(
            modifier = modifier
                .clip(RoundedCornerShape(Spacing.sm))
                .background(MaterialTheme.colorScheme.background)
                .padding(Spacing.sm),
        ) {

            Column(
                horizontalAlignment = Alignment.Start,

                ) {

                AddressLazyColumn(
                    addresses = state.addresses,
                    canDelete = false,
                    onMarkFavorite = {
                        onAction(AddressListingActions.OnMarkAsFavorite(it))

                    },
                    onClick = {
                        onAction(AddressListingActions.OnMarkAsFavorite(it))
                    },
                    isFavorite = { it == state.favoriteAddress }
                )
                JetMartButton(
                    onClick = {
                        onAddAddress()
                    },
                    label = stringResource(id = R.string.add_address)
                )

            }

        }

    }

}