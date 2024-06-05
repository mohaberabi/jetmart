package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@Composable
fun JetMartLogo(modifier: Modifier = Modifier) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .wrapContentHeight()
            .padding(Spacing.md)
    ) {
        Text(
            text = "J",
            style = MaterialTheme.typography.displayLarge.copy(
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.ExtraBold
            ),
        )
        Text(
            text = "Jet Mart",
            style = MaterialTheme.typography.titleLarge.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewJetMartLogo() {

    JetMartTheme() {
        JetMartLogo()
    }
}