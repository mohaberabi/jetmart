package com.mohaberabi.jetmart.features.home.presentation.viewmodel

import com.mohaberabi.jetmart.core.util.UiText
import com.mohaberabi.jetmart.features.category.domain.model.CategoryModel

sealed interface HomeCategoriesState {

    data object Initial : HomeCategoriesState
    data object Loading : HomeCategoriesState
    data class Done(val categories: List<CategoryModel>) : HomeCategoriesState
    data class Error(val error: UiText) : HomeCategoriesState
}