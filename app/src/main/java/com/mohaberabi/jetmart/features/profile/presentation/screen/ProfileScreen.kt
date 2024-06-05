package com.mohaberabi.jetmart.features.profile.presentation.screen

import JetMartScaffold
import android.provider.ContactsContract.Profile
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.mohaberabi.jetmart.features.profile.presentation.viewmodel.ProfileActions
import com.mohaberabi.jetmart.features.profile.presentation.viewmodel.ProfileEvent
import com.mohaberabi.jetmart.features.profile.presentation.viewmodel.ProfileState
import com.mohaberabi.jetmart.features.profile.presentation.viewmodel.ProfileViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun ProfileScreenRoot(
    modifier: Modifier = Modifier,
    onGoBack: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->
        when (event) {
            is ProfileEvent.Error -> onShowSnackBar(event.error.asString(context))
            ProfileEvent.Updated -> onShowSnackBar("Update")
        }
    }
    ProfileScreen(
        modifier = modifier,
        onAction = viewModel::onAction,
        state = state,
        onGoBack = onGoBack
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    onAction: (ProfileActions) -> Unit = {},
    state: ProfileState,
    onGoBack: () -> Unit = {}
) {
    JetMartScaffold(
        modifier = modifier,
        topAppBar = {
            JetMartAppBar(
                onBackClick = onGoBack,
                showBackButton = true,
                title = "Profile"
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(Spacing.md),
        ) {

            item {
                PrimaryTextField(
                    value = state.user.email,
                    isReadOnly = true,
                    label = stringResource(id = R.string.email),
                )
                PrimaryTextField(
                    value = state.user.name,
                    label = stringResource(id = R.string.first_name),
                    onChanged = { onAction(ProfileActions.OnNameChanged(it)) }
                )
                PrimaryTextField(
                    value = state.user.lastname,
                    label = stringResource(id = R.string.last_name),
                    onChanged = { onAction(ProfileActions.OnLastNameChanged(it)) }
                )

                JetMartButton(
                    label = stringResource(R.string.update),
                    loading = state.loading,
                    enabled = state.canUpdate,
                    onClick = { onAction(ProfileActions.OnSaveClick) }
                )
            }
        }
    }
}


@Preview
@Composable
private fun ProfileScreenPreview() {

    JetMartTheme {

        ProfileScreen(
            state = ProfileState(),
        )
    }
}