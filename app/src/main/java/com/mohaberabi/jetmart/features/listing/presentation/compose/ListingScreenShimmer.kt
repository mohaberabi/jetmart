package com.mohaberabi.jetmart.features.listing.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.core.presentation.compose.DefaultShimmer
import com.mohaberabi.jetmart.core.presentation.compose.JetMartChip
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.extensions.shimmerEffect


@Composable
fun ListingScreenShimmer(modifier: Modifier = Modifier) {


    Column(
        modifier = modifier.padding(Spacing.md)
    ) {

        Row {
            repeat(8) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Spacing.sm))
                        .width(50.dp)
                        .shimmerEffect()
                        .padding(Spacing.lg)
                )
                Spacer(modifier = Modifier.width(Spacing.md))
            }
        }
        Spacer(modifier = Modifier.height(Spacing.md))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            userScrollEnabled = false
        ) {

            items(count = 20) {
                DefaultShimmer()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewListingScreenShimmer() {
    JetMartTheme {
        ListingScreenShimmer()
    }
}