package com.mohaberabi.jetmart.core.presentation.compose

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mohaberabi.jetmart.core.util.const.JetMartLocales
import com.mohaberabi.jetmart.core.util.currentLanguage
import com.mohaberabi.jetmart.core.util.extensions.toEnum


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun LanguagePicker(
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current
    JetMartRadio<JetMartLocales>(
        modifier = modifier,
        items = JetMartLocales.entries,
        onChanged = {
        },
        label = { local ->
            local!!.label
        },
        current = context.currentLanguage.toEnum<JetMartLocales>()
    )
}