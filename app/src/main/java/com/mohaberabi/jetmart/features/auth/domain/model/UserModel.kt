package com.mohaberabi.jetmart.features.auth.domain.model


data class UserModel(
    val uid: String,
    val name: String,
    val lastname: String,
    val email: String,
    val langCode: String,
    val token: String
) {
    companion object {
        val empty = UserModel(
            "",
            "",
            "",
            "",
            "",
            ""
        )

    }

    val isEmpty: Boolean
        get() = this == empty
}