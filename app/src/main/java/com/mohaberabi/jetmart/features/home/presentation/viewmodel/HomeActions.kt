package com.mohaberabi.jetmart.features.home.presentation.viewmodel

import com.mohaberabi.jetmart.features.listing.domain.model.ListingCategoryModel


sealed interface HomeActions {
    data object OnRetry : HomeActions
    data class OnCategoryClick(
        val index: Int,
        val parentCategories: List<ListingCategoryModel>
    ) : HomeActions


    data object OnAddressClicked : HomeActions
}