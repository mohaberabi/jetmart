package com.mohaberabi.jetmart.features.auth.presentation.viewmodel


data class AuthState(
    val email: String = "",
    val password: String = "",
    val name: String = "",
    val lastName: String = "",
    val loading: Boolean = false,
    val isLogin: Boolean = true
)