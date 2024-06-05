package com.mohaberabi.jetmart.core.presentation.compose

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.mohaberabi.jetmart.core.util.currentLanguage
import com.mohaberabi.jetmart.core.util.extensions.translate


@Composable
fun LocalizedText(
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    dynamicText: Map<String, String>
) {
    val context = LocalContext.current
    val lang = context.currentLanguage
    Text(
        text = dynamicText.translate(lang),
        fontSize = fontSize,
        fontStyle = fontStyle,
        modifier = modifier,
        overflow = overflow,
        maxLines = maxLines,
        style = style,
        onTextLayout = onTextLayout,
        minLines = minLines,
        softWrap = softWrap,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        letterSpacing = letterSpacing,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        color = color,
    )

}