package com.mohaberabi.jetmart.features.listing.presentation.viemwodel

import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.features.listing.domain.model.ListingCategoryModel
import com.mohaberabi.jetmart.features.listing.domain.model.ListingResponseModel


data class ListingCategoryState(
    val index: Int = 0,
    val parentCategories: List<ListingCategoryModel> = listOf()

) {
    val currentCategoryId: String
        get() = parentCategories[index].id
}


sealed interface ListingItemsState {
    data object Loading : ListingItemsState
    data object Initial : ListingItemsState
    data class Error(val error: UiText) : ListingItemsState
    data class Done(val listing: ListingResponseModel) : ListingItemsState
}