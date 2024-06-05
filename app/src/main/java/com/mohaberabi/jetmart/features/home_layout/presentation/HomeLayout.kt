package com.mohaberabi.jetmart.features.home_layout.presentation

import JetMartScaffold
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomBar
import com.mohaberabi.jetmart.core.presentation.compose.JetMartBottomItems
import com.mohaberabi.jetmart.core.presentation.compose.ObserveAsEvent
import com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel.HomeLayoutActions
import com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel.HomeLayoutEvents
import com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel.HomeLayoutState
import com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel.HomeLayoutViewModel

import org.koin.androidx.compose.koinViewModel


@Composable
fun HomeLayoutRoot(
    onShowSnackBar: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: HomeLayoutViewModel = koinViewModel(),
    dynamicContent: @Composable (PaddingValues) -> Unit,
    onGoSignIn: () -> Unit,
    onGoToCart: () -> Unit,
    onBottomNavigated: (JetMartBottomItems) -> Unit,
    currentBottomRoute: String
) {
    val state by viewmodel.state.collectAsStateWithLifecycle()

    ObserveAsEvent(
        flow = viewmodel.event,
    ) { event ->
        when (event) {
            HomeLayoutEvents.GoSignIn -> onGoSignIn()
            HomeLayoutEvents.GoToCart -> onGoToCart()
        }
    }
    HomeLayout(
        modifier = modifier,
        dynamicContent = dynamicContent,
        onBottomNavigated = onBottomNavigated,
        onAction = viewmodel::onAction,
        currentRoute = currentBottomRoute,
        state = state
    )
}

@Composable
fun HomeLayout(
    modifier: Modifier = Modifier,
    dynamicContent: @Composable (PaddingValues) -> Unit,
    onAction: (HomeLayoutActions) -> Unit,
    currentRoute: String,
    onBottomNavigated: (JetMartBottomItems) -> Unit = {},
    state: HomeLayoutState
) {
    JetMartScaffold(
        modifier = modifier,
        bottomAppBar = {
            JetMartBottomBar(
                cartSize = state.cartSize,
                onClick = { item ->
                    if (item == JetMartBottomItems.CART) {
                        onAction(HomeLayoutActions.OnCartClicked)
                    } else {
                        onBottomNavigated(item)
                    }
                },
                top = currentRoute
            )
        }
    ) { padding ->

        dynamicContent(padding)

    }

}