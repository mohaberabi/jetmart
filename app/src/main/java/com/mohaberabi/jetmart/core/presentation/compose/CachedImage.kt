package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage


@Composable
fun CachedImage(
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
    model: Any,
    size: Dp = 110.dp,
    borderRadius: Dp = 15.dp
) {

    val currentModel = if (isPreview) com.mohaberabi.jetmart.R.drawable.img_placholder else model
    SubcomposeAsyncImage(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(borderRadius)),
        model = currentModel,
        contentScale = ContentScale.Crop,
        contentDescription = "",
        loading = {
            ImagePlaceHolder(size = size)
        },
        error = {
            ImagePlaceHolder(size = size)
        }
    )


}