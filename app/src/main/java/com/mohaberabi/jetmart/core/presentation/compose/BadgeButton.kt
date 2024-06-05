package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mohaberabi.jetmart.core.presentation.theme.CartIcon
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadgeButton(
    modifier: Modifier = Modifier,
    label: String = "199",
    onClick: (() -> Unit)? = null,
    icon: ImageVector = CartIcon,
    badgeColor: Color = Color.Red,
    iconSize: Dp = 24.dp,
    showBadge: Boolean = true
) {


    Box(

        modifier = modifier
            .padding(Spacing.xs)
            .clickable {
                if (onClick != null) {
                    onClick()
                }
            },
        contentAlignment = Alignment.BottomStart
    ) {

        Icon(
            imageVector = icon,
            contentDescription = null,

            modifier = Modifier
                .size(iconSize)
        )

        if (showBadge) {
            Badge(
                modifier = Modifier.padding(Spacing.sm),
                containerColor = badgeColor
            ) {
                Text(
                    text = label,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme
                        .typography.bodySmall.copy(fontSize = 8.sp)
                        .copy(color = MaterialTheme.colorScheme.onPrimary)
                )
            }
        }
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewBadgeButton() {
    JetMartTheme {
        BadgeButton()
    }
}
