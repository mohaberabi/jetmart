package com.mohaberabi.jetmart.features.settings.presentation.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.JetMartButton
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.auth.domain.model.UserModel


@Composable
fun UserBox(
    modifier: Modifier = Modifier,
    user: UserModel,
    onGoSignIn: () -> Unit,
    onGoAccount: () -> Unit
) {

    if (user == UserModel.empty) {
        NonAuthedUser(
            onGoSignIn = onGoSignIn,
        )
    } else {
        AuthedUser(
            name = "${user.name} ${user.lastname}",
            onGoAccount = onGoAccount
        )
    }

}

@Composable
private fun AuthedUser(
    modifier: Modifier = Modifier,
    name: String,
    onGoAccount: () -> Unit
) {


    val firstChar = if (name.isNotEmpty()) name.toCharArray()[0].uppercaseChar() else ""
    Row(
        modifier = modifier
            .padding(Spacing.lg)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = firstChar.toString(),
                    modifier = Modifier.padding(Spacing.sm),

                    style = MaterialTheme.typography
                        .headlineLarge.copy(color = MaterialTheme.colorScheme.secondary)
                )

            }
            Spacer(modifier = Modifier.width(Spacing.sm))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme
                        .typography
                        .headlineMedium
                        .copy(color = MaterialTheme.colorScheme.primary)
                )


            }

        }
        IconButton(
            onClick = onGoAccount,
        ) {
            Icon(
                painter = painterResource(id = R.drawable.settings),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun NonAuthedUser(
    onGoSignIn: () -> Unit,
) {
    Column {
        Text(
            text = stringResource(R.string.start_shopping_now),
            style = MaterialTheme.typography.titleLarge,
        )

        Text(
            text = stringResource(R.string.don_t_miss_the_amazing_jetpack_shopping_experience),
            style = MaterialTheme.typography.bodyMedium,
        )

        JetMartButton(
            onClick = onGoSignIn,
            label = stringResource(R.string.sign_in_or_create_account)
        )

    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewUserBox() {

    JetMartTheme {

        UserBox(
            onGoSignIn = {},
            onGoAccount = {},
            user = UserModel(
                "uid",
                name = "Mohab",
                langCode = "",
                lastname = "Erabi",
                email = "",
                token = ""
            )
        )
    }

}