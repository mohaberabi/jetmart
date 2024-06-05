package com.mohaberabi.jetmart.features.address.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.core.presentation.compose.JetMartRadio
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel


@Composable
fun AddressCard(
    modifier: Modifier = Modifier,
    address: AddressModel,
    onDelete: (AddressModel) -> Unit = {},
    onClick: (AddressModel) -> Unit = {},
    canDelete: Boolean = false,
    isFavorite: Boolean = false,
    onMarkFavorite: () -> Unit = {}
) {

    Column(
        modifier = modifier
            .padding(Spacing.sm)
            .clickable {
                onClick(address)
            },
        horizontalAlignment = Alignment.Start,

        ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(
                text = address.label, style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold,
                )
            )


            RadioButton(
                selected = isFavorite,
                onClick = onMarkFavorite,
            )

        }

        Text(
            text = address.location, style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)

        )
        Spacer(modifier = Modifier.height(Spacing.sm))
        Text(
            text = address.address, style = MaterialTheme.typography.bodyMedium

        )


        if (canDelete)

            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
            ) {

                Icon(
                    imageVector = Icons.Default.Delete,
                    tint = Color.Gray,
                    modifier = Modifier
                        .clickable {
                            onDelete(address)
                        },
                    contentDescription = "delete"
                )
            }
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewAddressCompose() {

    JetMartTheme {
        AddressCard(
            onDelete = {},
            address = AddressModel(
                "",
                lat = 0.0,
                lng = 0.0,
                label = "Home",
                address = "villa 101, tgmoaa khaames hay awal ",
                location = "New Cairo Egypt"

            )
        )
    }
}