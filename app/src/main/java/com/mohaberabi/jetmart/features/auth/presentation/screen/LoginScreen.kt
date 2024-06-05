package com.mohaberabi.jetmart.features.auth.presentation.screen

import JetMartScaffold
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartAppBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.compose.JetMartLogo
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.core.presentation.compose.PasswordTextField
import com.mohaberabi.jetmart.core.presentation.compose.PrimaryTextField
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.auth.presentation.viewmodel.AuthActions
import com.mohaberabi.jetmart.features.auth.presentation.viewmodel.AuthEvent
import com.mohaberabi.jetmart.features.auth.presentation.viewmodel.AuthState
import com.mohaberabi.jetmart.features.auth.presentation.viewmodel.AuthViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun LoginScreenRoot(
    modifier: Modifier = Modifier,
    onShowSnackBar: (String) -> Unit,
    viewModel: AuthViewModel = koinViewModel(),
    onGoHomeScreen: () -> Unit,
    canSkip: Boolean = true,
    onGoBack: () -> Unit = {},
    onSkip: () -> Unit
) {
    val context = LocalContext.current
    ObserveAsEvent(flow = viewModel.event) { event ->

        when (event) {
            AuthEvent.AuthDone -> onGoHomeScreen()
            is AuthEvent.Error -> onShowSnackBar(event.error.asString(context))
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginScreen(
        onBackClick = onGoBack,
        canSkip = canSkip,
        modifier = modifier,
        onSkip = onSkip,
        onAction = { act ->
            if (act is AuthActions.OnSkip) {
                onGoHomeScreen()
            } else {
                viewModel.onAction(act)
            }
        },
        state = state,
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onAction: (AuthActions) -> Unit,
    canSkip: Boolean = true,
    onBackClick: () -> Unit = {},
    onSkip: () -> Unit = {}
) {


    val buttonLabel = if (state.isLogin) R.string.login else R.string.create_account
    val authWayString =
        if (state.isLogin) R.string.dont_have_Account else R.string.already_have_an_account

    val greeting = if (state.isLogin) R.string.welcome_again else R.string.hello_there
    val authwayButtonLAbel = if (state.isLogin) R.string.create_account else R.string.login
    JetMartScaffold(
        topAppBar = {

            JetMartAppBar(
                onBackClick = onBackClick,
                showBackButton = !canSkip,
                actions = {
                    if (canSkip)
                        TextButton(
                            onClick = {
                                onSkip()
                            },
                        ) {
                            Text(text = stringResource(R.string.skip))
                        }
                },
                title = stringResource(R.string.login_or_create_an_account)
            )
        }
    ) {


            padding ->
        LazyColumn(
            modifier = modifier
                .padding(padding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            item {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(Spacing.lg),
                ) {

                    JetMartLogo()

                    Text(
                        text = stringResource(greeting),
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
                    )

                    PrimaryTextField(
                        label = stringResource(R.string.email),
                        value = state.email,
                        onChanged = { onAction(AuthActions.OnEmailChanged(it)) },
                        placeHolder = stringResource(R.string.example_jetmart_com)
                    )

                    PasswordTextField(
                        value = state.password,
                        onChange = { onAction(AuthActions.OnPasswordChanged(it)) },
                    )


                    if (!state.isLogin)
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            PrimaryTextField(
                                value = state.name,
                                onChanged = { onAction(AuthActions.OnNameChanged(it)) },
                                label = stringResource(R.string.first_name),
                                placeHolder = stringResource(R.string.jet)
                            )
                            PrimaryTextField(
                                value = state.lastName,
                                onChanged = { onAction(AuthActions.OnLastNameChanged(it)) },
                                label = stringResource(R.string.last_name),
                                placeHolder = stringResource(R.string.mart),
                            )
                        }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(authWayString),
                        )
                        TextButton(
                            onClick = {
                                onAction(AuthActions.OnToggleAuthWay)
                            },
                        ) {

                            Text(text = stringResource(authwayButtonLAbel))
                        }
                    }

                    JetMartButton(
                        loading = state.loading,

                        onClick = {
                            onAction(AuthActions.OnLoginClick)
                        },
                        label = stringResource(buttonLabel)
                    )


                }
            }
        }
    }
}


@Preview(locale = "ar")
@Composable
private fun PreviewLoginscreen() {
    JetMartTheme(langCode = "ar") {
        LoginScreen(
            state = AuthState(),
            onAction = {},
        )
    }
}