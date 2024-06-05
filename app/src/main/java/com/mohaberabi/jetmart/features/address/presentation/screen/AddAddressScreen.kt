package com.mohaberabi.jetmart.features.address.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.compose.PrimaryTextField
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.save.AddAddressActions
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.save.AddAddressEvents
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.save.AddAddressState
import com.mohaberabi.jetmart.features.address.presentation.viewmodel.save.AddAddressViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun AddAddressScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: AddAddressViewModel = koinViewModel(),
    onBackClick: () -> Unit,
    onShowSnackBar: (String) -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            AddAddressEvents.AddressSaved -> onBackClick()
            is AddAddressEvents.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    AddAddressScreen(
        state = state,
        modifier = modifier,
        onBackClick = onBackClick,
        onAction = viewModel::onAction
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAddressScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    state: AddAddressState,
    onAction: (AddAddressActions) -> Unit
) {


    JetMartScaffold(
        modifier = modifier,
        topAppBar = {
            JetMartAppBar(
                showBackButton = true,
                onBackClick = onBackClick,
                title = stringResource(R.string.add_address)
            )
        }
    ) {

            padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.lg)
                .scrollable(rememberScrollState(), Orientation.Vertical),
        ) {


            PrimaryTextField(
                label = stringResource(id = R.string.location),
                isReadOnly = true,
                value = state.locationName,
            )
            PrimaryTextField(
                value = state.addressName,
                onChanged = { onAction(AddAddressActions.OnAddressNameChanged(it)) },
                label = stringResource(R.string.name),
                placeHolder = stringResource(R.string.address_name_ph)
            )
            PrimaryTextField(
                value = state.address,
                onChanged = { onAction(AddAddressActions.OnAddressDetailsChanged(it)) },
                label = stringResource(R.string.address),
                placeHolder = stringResource(R.string.address_ph)
            )

            JetMartButton(
                loading = state.loading,
                enabled = state.canSaveAddress,
                label = stringResource(R.string.save),
                onClick = {
                    onAction(AddAddressActions.OnSaveAddress)
                }
            )

        }

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAddAddressScreen() {
    JetMartTheme {


        AddAddressScreen(
            onAction = {

            },
            state = AddAddressState(),
            onBackClick = {},
        )
    }
}