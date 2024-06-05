package com.mohaberabi.jetmart.features.home.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.ForwardIcon
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressTopBar(
    modifier: Modifier = Modifier,
    onPickAddress: () -> Unit = {},
) {

    Surface(
        modifier = modifier,
        color = MaterialTheme.colorScheme.primary,
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(Spacing.sm)
        ) {

            Text(
                text = stringResource(R.string.delivering_to),
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
            )

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                )
                IconButton(onClick = onPickAddress) {

                    Icon(
                        imageVector = ForwardIcon,
                        tint = MaterialTheme.colorScheme.secondary,
                        contentDescription = stringResource(R.string.choose_address)
                    )
                }
            }
        }
    }


}


@Preview
@Composable
private fun PreviewAddressTopAppBar() {
    JetMartTheme {
        AddressTopBar()
    }
}