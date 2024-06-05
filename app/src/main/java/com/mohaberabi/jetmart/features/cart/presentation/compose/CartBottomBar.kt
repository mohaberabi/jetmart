package com.mohaberabi.jetmart.features.cart.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@Composable
fun CartBottomBar(
    modifier: Modifier = Modifier,
    cartTotal: Double,
    onClick: () -> Unit,
    @StringRes leading: Int = R.string.cart_total,
) {

    val label = stringResource(
        leading,
        stringResource(
            id = R.string.egp,
            cartTotal
        )
    )
    Card(
        modifier = modifier
    ) {


        JetMartButton(
            enabled = cartTotal > 0,
            modifier = Modifier.padding(Spacing.md),
            onClick = onClick,
            label = label,
        )

    }
}


@Preview
@Composable
private fun PreviewCartBottomBar() {

    JetMartTheme {

        CartBottomBar(
            cartTotal = 1785.25,
            onClick = {}
        )
    }
}