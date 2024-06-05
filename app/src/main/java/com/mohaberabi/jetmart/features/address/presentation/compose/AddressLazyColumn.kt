package com.mohaberabi.jetmart.features.address.presentation.compose

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.features.address.domain.model.AddressModel


@Composable
fun AddressLazyColumn(
    modifier: Modifier = Modifier,
    addresses: List<AddressModel> = listOf(),
    onDelete: (AddressModel) -> Unit = {},
    onMarkFavorite: (AddressModel) -> Unit = {},

    onClick: (AddressModel) -> Unit = {},
    canDelete: Boolean = false,
    isFavorite: (AddressModel) -> Boolean = { false }
) {


    LazyColumn(
        modifier = modifier,
    ) {

        items(addresses) { address ->
            AddressCard(
                onMarkFavorite = { onMarkFavorite(address) },
                onClick = onClick,
                isFavorite = isFavorite(address),
                canDelete = canDelete,
                address = address,
                onDelete = onDelete,
            )
        }
    }
}

@Preview(
    showBackground = true,
)
@Composable
fun PreviewAddressLazyColumn(
    modifier: Modifier = Modifier,
) {

    JetMartTheme {

        AddressLazyColumn(
            addresses = listOf(
                AddressModel(
                    "",
                    0.0,
                    0.0,
                    "villa 1010 , hay awal mnt2a 6",
                    "Home",
                    "New Cairo Egypt ",
                ),
                AddressModel(
                    "",
                    0.0,
                    0.0,
                    "villa 1010 , hay awal mnt2a 6",
                    "Home",
                    "New Cairo Egypt ",
                ),
                AddressModel(
                    "",
                    0.0,
                    0.0,
                    "villa 1010 , hay awal mnt2a 6",
                    "Home",
                    "New Cairo Egypt ",
                )
            ),
        )

    }

}