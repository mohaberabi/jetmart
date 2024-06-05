package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.core.util.extensions.shimmerEffect


@Composable
fun DefaultShimmer(
    modifier: Modifier = Modifier,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(Spacing.sm),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(Spacing.sm))
                .size(120.dp)
                .shimmerEffect()

        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Box(
            modifier = Modifier
                .height(10.dp)
                .clip(RoundedCornerShape(Spacing.sm))
                .width(75.dp)
                .shimmerEffect()
        )
        Spacer(modifier = Modifier.height(Spacing.md))

    }
}