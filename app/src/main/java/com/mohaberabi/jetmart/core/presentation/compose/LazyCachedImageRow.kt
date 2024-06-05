package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun LazyCachedImageRow(
    modifier: Modifier = Modifier,
    images: List<Any>,
    imageSize: Dp = 60.dp
) {

    LazyRow(
        modifier = modifier,
    ) {

        items(
            images,
        ) {
            CachedImage(
                model = it,
                size = imageSize
            )
        }
    }
}