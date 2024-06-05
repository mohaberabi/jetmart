package com.mohaberabi.jetmart.core.presentation.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import kotlinx.serialization.Serializable


enum class JetMartBottomItems(
    val route: String,
    @DrawableRes val icon: Int,
) {
    HOME("home", R.drawable.home),
    CART("cart", R.drawable.cart),
    Settings("settings", R.drawable.settings)
}

@Composable
fun JetMartBottomBar(
    modifier: Modifier = Modifier,
    top: String,
    onClick: (JetMartBottomItems) -> Unit = {},
    cartSize: Int = 0
) {


    BottomAppBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.primary,
    ) {

        JetMartBottomItems.entries.forEach { item ->

            NavigationBarItem(
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.secondary,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = Color.Gray
                ),
                selected = top == item.route,
                onClick = { onClick(item) },
                icon = {
                    if (item == JetMartBottomItems.CART) {
                        BadgeButton(
                            iconSize = 28.dp,
                            onClick = {onClick(item)},
                            showBadge = cartSize > 0,
                            label = "$cartSize",
                        )
                    } else {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = "",
                            modifier = Modifier.size(24.dp)
                        )
                    }

                }
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun PreviewJetMartBottomBar() {
    JetMartTheme {
        JetMartBottomBar(

            top = JetMartBottomItems.HOME.route
        )
    }
}