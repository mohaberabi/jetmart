package com.mohaberabi.jetmart.jetmart.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.util.currentLanguage

import com.mohaberabi.jetmart.jetmart.activity.viewmodel.JetMartActivityViewModel
import com.mohaberabi.jetmart.jetmart.compose.JetMartComposeApp
import com.mohaberabi.jetmart.jetmart.compose.rememberJetMartState

import org.koin.androidx.viewmodel.ext.android.viewModel

class JetMartActivity : ComponentActivity() {

    private val mainActivityViewModel by viewModel<JetMartActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
            .apply {
                setKeepOnScreenCondition {
                    !mainActivityViewModel.state.value.didLoadData
                }
            }

        setContent {

            val jetMartState = rememberJetMartState()

            JetMartTheme(
                langCode = currentLanguage
            ) {
                JetMartComposeApp(
                    jetMartState = jetMartState,
                    viewModel = mainActivityViewModel
                )

            }
        }
    }
}

