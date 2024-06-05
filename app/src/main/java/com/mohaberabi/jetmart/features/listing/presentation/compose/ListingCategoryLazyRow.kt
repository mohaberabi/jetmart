package com.mohaberabi.jetmart.features.listing.presentation.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mohaberabi.jetmart.core.presentation.compose.LocalizedText
import com.mohaberabi.jetmart.core.presentation.theme.Spacing
import com.mohaberabi.jetmart.features.listing.domain.model.ListingCategoryModel


@Composable
fun ListingCategoryLazyRow(
    modifier: Modifier = Modifier,
    categories: List<ListingCategoryModel> = listOf(),
    onClick: (ListingCategoryModel, Int) -> Unit,
    choosedId: String = "",
    lazyListState: LazyListState = rememberLazyListState()
) {

    LazyRow(
        state = lazyListState,
        modifier = modifier,
    ) {
        items(categories.size) { index ->
            val cat = categories[index]
            val selected = choosedId == cat.id
            LocalizedText(
                dynamicText = cat.name,
                modifier = Modifier
                    .padding(end = Spacing.md)
                    .clickable {
                        onClick(cat, index)
                    },
                style = MaterialTheme.typography.titleMedium.copy(
                    color = if (selected) MaterialTheme.colorScheme.secondary
                    else MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    }
}