package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.LockIcon
import com.mohaberabi.jetmart.core.presentation.theme.LockOpenIcon


@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onChange: (String) -> Unit = {},
) {

    var showPassword by remember {
        mutableStateOf(false)
    }


    var transformations =
        if (showPassword) VisualTransformation.None else PasswordVisualTransformation()

    PrimaryTextField(
        value = value,
        onChanged = onChange,
        modifier = modifier,
        label = "Password",
        visualTransformations = transformations,
        placeHolder = "********",
        suffix = {
            Icon(
                modifier = Modifier
                    .clickable {
                        showPassword = !showPassword
                    },
                imageVector = if (showPassword) LockIcon else LockOpenIcon,
                contentDescription = stringResource(R.string.change_password_visibility)
            )
        }

    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewPassowordTextField() {


    JetMartTheme {


        PasswordTextField(
            value = "sasdsad",

            )
    }
}