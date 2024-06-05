package com.mohaberabi.jetmart.features.listing.presentation.viemwodel

import com.mohaberabi.jetmart.features.item.presentation.viewmodel.ItemActions

sealed interface ListingActions {


    data object OnCartClicked : ListingActions
    data class OnParentCategoryClicked(val index: Int) : ListingActions

    data class OnItemClicked(val id: String) : ListingActions

}