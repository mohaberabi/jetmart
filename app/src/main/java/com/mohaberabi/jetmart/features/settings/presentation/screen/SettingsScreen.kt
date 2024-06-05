package com.mohaberabi.jetmart.features.settings.presentation.screen

import JetMartScaffold
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAlertDialog
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomSheet
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.compose.LanguagePicker
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.compose.SimpleListItem
import com.mohaberabi.jetmart.core.presentation.theme.HearIcon
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.util.currentLanguage

import com.mohaberabi.jetmart.core.util.extensions.openAppSettings
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import com.mohaberabi.jetmart.features.settings.presentation.compose.AccountBox
import com.mohaberabi.jetmart.features.settings.presentation.compose.HelpBox
import com.mohaberabi.jetmart.features.settings.presentation.compose.UserBox
import com.mohaberabi.jetmart.features.settings.presentation.viewmodel.SettingsAction
import com.mohaberabi.jetmart.features.settings.presentation.viewmodel.SettingsEvent
import com.mohaberabi.jetmart.features.settings.presentation.viewmodel.SettingsState
import com.mohaberabi.jetmart.features.settings.presentation.viewmodel.SettingsViewModel
import org.koin.androidx.compose.koinViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingsScreenRoot(
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = koinViewModel(),
    onGoProfile: () -> Unit,
    onGoOnBoarding: () -> Unit,
    onGoSignIn: () -> Unit,
    onGoAddressBook: () -> Unit,
    onGoTerms: () -> Unit,
    onGoPrivacy: () -> Unit,
    onGoOrders: () -> Unit
) {
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    ObserveAsEvent(
        flow = viewModel.event,
    ) { event ->
        if (event is SettingsEvent.SignedOut) {
            onGoOnBoarding()
        }
    }
    SettingsScreen(
        modifier = modifier,
        state = state,
        onGoProfile = onGoProfile,
        onAction = viewModel::onAction,
        onGoSignIn = onGoSignIn,
        onGoAddressBook = onGoAddressBook,
        currentLang = context.currentLanguage,
        onGoPrivacy = onGoPrivacy,
        onGoTerms = onGoTerms,
        onGoOrders = onGoOrders,
        onLanguageClicked = {
            context.openAppSettings()
        }
    )

}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    state: SettingsState,
    onGoProfile: () -> Unit,
    onAction: (SettingsAction) -> Unit,
    onGoSignIn: () -> Unit,
    currentLang: String = "",
    onGoAddressBook: () -> Unit,
    onGoTerms: () -> Unit,
    onGoPrivacy: () -> Unit,
    onGoOrders: () -> Unit,
    onLanguageClicked: () -> Unit

) {


    var showSignOutDialog by rememberSaveable {
        mutableStateOf(false)
    }
    var showLanguagePicker by rememberSaveable {
        mutableStateOf(false)
    }

    JetMartScaffold { padding ->
        LazyColumn(
            modifier = modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {

            item {
                UserBox(
                    user = state.user,
                    onGoSignIn = { onGoSignIn() },
                    onGoAccount = onGoProfile,
                )

                if (!state.user.isEmpty) {
                    AccountBox(
                        onGoToOrders = onGoOrders,
                        onGoToAddresses = onGoAddressBook,

                    )
                }
                HelpBox(
                    onLanguageClick = onLanguageClicked,
                    currentLang = currentLang,
                    onGoTerms = onGoTerms,
                    onGoPrivacy = onGoPrivacy
                )


                if (!state.user.isEmpty) {
                    TextButton(
                        onClick = {
                            showSignOutDialog = true
                        },
                    ) {
                        Text(
                            text = stringResource(R.string.sign_out),
                            style = MaterialTheme.typography.titleMedium.copy(color = Color.Gray)
                        )
                    }
                }
            }
        }

    }
    if (showSignOutDialog) {
        JetMartAlertDialog(
            title = stringResource(R.string.sign_out),
            positiveText = stringResource(R.string.sign_out),
            isEmergent = true,
            negativeText = stringResource(id = R.string.go_back),
            onPositive = {
                onAction(SettingsAction.OnSignOutClick)
                showSignOutDialog = false
            },
            onNegative = { showSignOutDialog = false },
            subtitle = stringResource(R.string.sign_out_question),
            onDismiss = { showSignOutDialog = false }
        )
    }

    if (showLanguagePicker) {
        JetMartBottomSheet(
            onDismiss = {
                showLanguagePicker = false
            }
        ) {
            LanguagePicker()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen() {

    JetMartTheme {

        SettingsScreen(
            state = SettingsState(),
            onGoProfile = {},
            onGoOrders = {},
            onAction = {},
            onGoAddressBook = {},
            onGoPrivacy = {},
            onGoTerms = {},
            onLanguageClicked = {},
            onGoSignIn = {}
        )
    }
}