package com.mohaberabi.jetmart.core.presentation.compose

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@Composable
fun JetMartPlaceHolder(
    modifier: Modifier = Modifier,
    @StringRes title: Int = R.string.no_results_found,
    onRetry: ( ()-> Unit)? = null
) {


    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        Icon(
            painter = painterResource(id = R.drawable.empty),
            contentDescription = stringResource(R.string.empty_data),
            modifier = Modifier.size(85.dp)
        )
        Spacer(modifier = Modifier.height(Spacing.md))
        Text(
            text = stringResource(id = title),
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.DarkGray,
                fontWeight = FontWeight.Bold
            ),
        )

        Text(
            text = stringResource(id = R.string.please_try_again),
            style = MaterialTheme.typography.bodyLarge.copy(
                color = Color.DarkGray,
            ),
        )

        if (onRetry != null) {
            JetMartButton(
                label = stringResource(R.string.try_again),
                onClick = onRetry
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewJetMartPlaceHolder() {
    JetMartTheme {

        JetMartPlaceHolder()
    }
}