package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@Composable
fun ImagePlaceHolder(
    modifier: Modifier = Modifier,
    clipRadius: Dp = Spacing.sm,
    size: Dp = 75.dp
) {

    Image(
        painter = painterResource(id = R.drawable.img_placholder),
        contentDescription = stringResource(R.string.image_place_holder),
        modifier = modifier
            .padding(Spacing.sm)
            .clip(RoundedCornerShape(clipRadius))
            .size(size)
    )
}