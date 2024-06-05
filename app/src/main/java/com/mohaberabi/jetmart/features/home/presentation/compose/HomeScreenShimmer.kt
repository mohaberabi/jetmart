package com.mohaberabi.jetmart.features.home.presentation.compose

import android.view.Surface
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.core.presentation.compose.DefaultShimmer
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.extensions.shimmerEffect
import com.mohaberabi.jetmart.core.util.extensions.spannedItem


@Composable
fun HomeScreenShimmer(modifier: Modifier = Modifier) {


    LazyVerticalGrid(

        columns = GridCells.Fixed(3),
        userScrollEnabled = false,
        modifier = Modifier
            .padding(Spacing.md)
            .wrapContentHeight(),
    ) {


        items(
            count = 26,
        ) {


           DefaultShimmer()
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreenShimmer() {

    JetMartTheme {
        HomeScreenShimmer()
    }
}