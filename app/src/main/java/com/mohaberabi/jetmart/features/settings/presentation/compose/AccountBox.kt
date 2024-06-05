package com.mohaberabi.jetmart.features.settings.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.SimpleListItem
import com.mohaberabi.jetmart.core.presentation.theme.HearIcon
import com.mohaberabi.jetmart.core.presentation.theme.LangIcon
import com.mohaberabi.jetmart.core.presentation.theme.LocationIcon
import com.mohaberabi.jetmart.core.presentation.theme.OrdersIcon
import com.mohaberabi.jetmart.core.util.const.JetMartLocales
import com.mohaberabi.jetmart.core.util.extensions.toEnum


@Composable
fun AccountBox(
    modifier: Modifier = Modifier,
    onGoToOrders: () -> Unit,
    onGoToAddresses: () -> Unit,

    ) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.Start,
    ) {
        Text(
            text = stringResource(R.string.account),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        SimpleListItem(
            onClick = onGoToOrders,

            leading = OrdersIcon,
            headline = stringResource(R.string.orders),
        )
        SimpleListItem(
            onClick = onGoToAddresses,
            leading = LocationIcon,
            headline = stringResource(R.string.address_book),
        )


    }

}