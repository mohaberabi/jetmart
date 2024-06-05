package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch


@Composable
fun JetMartPullToRefresh(
    modifier: Modifier = Modifier,
    onRefresh: () -> Unit = {},
    content: @Composable (LazyListState) -> Unit = {},
) {

    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    var isPullingToRefresh by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(
        key1 = listState,
    ) {


        combine(
            snapshotFlow {
                listState.firstVisibleItemIndex
            },
            snapshotFlow {
                listState.firstVisibleItemScrollOffset
            },
            snapshotFlow {

                listState.isScrollInProgress
            },
        ) { index, offset, scrolling ->
            if (index == 0 && offset == 0 && scrolling) {
                isPullingToRefresh = true
                onRefresh()
                scope.launch {
                    delay(2000)
                    isPullingToRefresh = false

                }
            }
        }.launchIn(this)
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.TopCenter
    ) {

        content(listState)
        if (isPullingToRefresh) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.primary)
            ) {

                JetMartLoader(
                    indicatorColor = MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }


    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewJetMartPullToRefresh() {


    JetMartTheme {
        JetMartPullToRefresh(
        )
    }
}