package com.mohaberabi.jetmart.features.settings.presentation.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.mohaberabi.jetmart.R
import com.mohaberabi.jetmart.core.presentation.compose.SimpleListItem
import com.mohaberabi.jetmart.core.presentation.theme.EmailIcon
import com.mohaberabi.jetmart.core.presentation.theme.FAQIcon
import com.mohaberabi.jetmart.core.presentation.theme.LangIcon
import com.mohaberabi.jetmart.core.presentation.theme.LocationIcon
import com.mohaberabi.jetmart.core.presentation.theme.PolicyIcon
import com.mohaberabi.jetmart.core.presentation.theme.TermsIcon
import com.mohaberabi.jetmart.core.util.const.JetMartLocales
import com.mohaberabi.jetmart.core.util.extensions.toEnum


@Composable
fun HelpBox(
    modifier: Modifier = Modifier,
    onGoTerms: () -> Unit,
    onGoPrivacy: () -> Unit,
    onLanguageClick: () -> Unit,
    currentLang: String = ""
) {
    Column(
        modifier = modifier,
    ) {
        Text(
            text = stringResource(R.string.help_support),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
        )
        SimpleListItem(
            onClick = onLanguageClick,
            leading = LangIcon,
            headline = "Language",
            supportText = currentLang.toEnum<JetMartLocales>()?.name ?: JetMartLocales.EN.name
        )
        SimpleListItem(
            onClick = onGoTerms,
            leading = TermsIcon,
            headline = stringResource(R.string.terms_conditions),
        )

        SimpleListItem(
            onClick = onGoPrivacy,
            leading = PolicyIcon,
            headline = stringResource(R.string.privacy_policy),
        )

        SimpleListItem(
            onClick = {},
            leading = EmailIcon,
            headline = stringResource(R.string.contact_us)
        )

    }
}