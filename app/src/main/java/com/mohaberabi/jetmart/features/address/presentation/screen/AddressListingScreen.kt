package com.mohaberabi.jetmart.features.address.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.features.address.presentation.compose.AddressLazyColumn
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingActions
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingState
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.listing.AddressListingViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddressListingScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AddressListingViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onGoAddAddress: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    AddressListingScreen(
        modifier = modifier,
        onAction = viewModel::onAction,
        state = state,
        onBackClick = onBackClick,
        onGoAddAddress = onGoAddAddress
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressListingScreen(
    modifier: Modifier = Modifier,
    onAction: (AddressListingActions) -> Unit,
    state: AddressListingState,
    onBackClick: () -> Unit,
    onGoAddAddress: () -> Unit,
) {


    JetMartScaffold(
        modifier = modifier,
        topAppBar = {
            JetMartAppBar(
                actions = {
                    TextButton(onClick = onGoAddAddress) {
                        Text(
                            text = stringResource(id = R.string.add_address),
                            style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.primary)
                        )
                    }
                },
                showBackButton = true,
                title = stringResource(id = R.string.address),
                onBackClick = onBackClick,
            )
        }
    ) {

            padding ->
        AddressLazyColumn(
            addresses = state.addresses,
            canDelete = true,
            onClick = {
                onAction(AddressListingActions.OnMarkAsFavorite(it))
            },
            onDelete = {
                onAction(AddressListingActions.OnDeleteAddress(it.id))
            },
            onMarkFavorite = {
                onAction(AddressListingActions.OnMarkAsFavorite(it))
            },
            isFavorite = {
                state.favoriteAddress == it
            },
            modifier = Modifier.padding(padding),
        )
    }
}