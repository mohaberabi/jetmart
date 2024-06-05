package com.mohaberabi.jetmart.features.category.presetnation.compsoe

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.mohaberabi.jetmart.core.presentation.compose.CachedImage
import com.mohaberabi.jetmart.core.presentation.compose.LocalizedText
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel


@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: CategoryModel,
    onClick: () -> Unit = {}
) {


    Surface(
        shape = RoundedCornerShape(Spacing.sm),
        modifier = modifier
            .padding(Spacing.md)
            .clickable {
                onClick()
            },
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(Spacing.sm)
                .size(110.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            CachedImage(
                model = category.image,
                size = 85.dp
            )
            LocalizedText(
                dynamicText = category.name,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center,

                    color = MaterialTheme.colorScheme.primary
                ),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}