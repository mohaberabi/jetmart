package com.mohaberabi.jetmart.features.home_layout.presentation.viewmodel


sealed interface HomeLayoutActions {


    data object OnCartClicked : HomeLayoutActions
}