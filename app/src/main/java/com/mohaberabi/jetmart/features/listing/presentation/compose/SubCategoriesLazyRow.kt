package com.mohaberabi.jetmart.features.listing.presentation.compose

import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.mohaberabi.jetmart.core.presentation.compose.JetMartSimpleChip
import com.mohaberabi.jetmart.core.util.currentLanguage
import com.mohaberabi.jetmart.core.util.currentLocal
import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel


@Composable
fun SubCategoriesLazyRow(
    modifier: Modifier = Modifier,
    subCategories: List<CategoryModel> = listOf(),
    selectedIndex: Int,
    onClick: (CategoryModel, Int) -> Unit
) {
    val local = LocalContext.current.currentLanguage

    LazyRow(
        modifier = modifier,
    ) {
        items(subCategories.size) { index ->
            val cat = subCategories[index]
            JetMartSimpleChip<CategoryModel>(
                label = {
                    it.name[local] ?: ""
                },
                item = cat,
                selected = index == selectedIndex,
                onClick = {
                    onClick(cat, index)
                }
            )
        }
    }

}