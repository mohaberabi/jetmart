package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.mohaberabi.jetmart.core.presentation.theme.JetMartTheme
import com.mohaberabi.jetmart.core.presentation.theme.LockIcon
import com.mohaberabi.jetmart.core.presentation.theme.Spacing


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrimaryTextField(
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    error: Boolean = false,
    value: String = "",
    onChanged: (String) -> Unit = {},
    options: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    suffix: @Composable () -> Unit = {},
    label: String = "",
    placeHolder: String = "",
    isReadOnly: Boolean = false,
    visualTransformations: VisualTransformation = VisualTransformation.None
) {


    val interactionSource = remember { MutableInteractionSource() }
    val colors = TextFieldDefaults.colors(
        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
        unfocusedIndicatorColor = Color.Gray,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedContainerColor = MaterialTheme.colorScheme.background
    )


    Column(
        horizontalAlignment = Alignment.Start,
        modifier = modifier
            .wrapContentHeight()
            .padding(Spacing.sm),
    ) {
        if (label.isNotEmpty())
            Text(
                text = label,
                modifier.padding(horizontal = Spacing.sm, vertical = Spacing.sm),
                style = MaterialTheme.typography.bodyMedium
            )
        BasicTextField(
            singleLine = singleLine,
            readOnly = isReadOnly,
            keyboardOptions = options,
            visualTransformation = visualTransformations,

            decorationBox = { inner ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = value,
                    innerTextField = {
                        Box {
                            if (value.isEmpty())
                                Text(
                                    placeHolder,
                                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                                )
                            inner()
                        }
                    },
                    enabled = true,
                    singleLine = singleLine,
                    visualTransformation = visualTransformations,
                    trailingIcon = suffix,
                    interactionSource = interactionSource,
                    colors = colors,
                    contentPadding = OutlinedTextFieldDefaults.contentPadding(),
                    container = {
                        OutlinedTextFieldDefaults.ContainerBox(
                            true,
                            shape = RoundedCornerShape(Spacing.sm),
                            isError = error,
                            interactionSource = interactionSource,
                            colors = colors
                        )
                    },
                )

            },
            modifier = modifier
                .fillMaxWidth(),
            value = value,
            onValueChange = onChanged,
        )
    }

}


@Preview(showBackground = true)
@Composable
private fun PreviewPrimaryTextField() {
    JetMartTheme {


        PrimaryTextField(
            value = "test",
            label = "test"
        )
    }
}