package com.mohaberabi.jetmart.features.item.presentation.viewmodel


sealed interface ItemActions {
    data object OnAddToCart : ItemActions
    data object OnIncQty : ItemActions
    data object OnCartClick : ItemActions
    data object OnDecQty : ItemActions
}