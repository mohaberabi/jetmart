package com.mohaberabi.jetmart.features.auth.data.source.remote.dto

import com.mohaberabi.jetmart.features.auth.domain.model.UserModel
import kotlinx.serialization.Serializable


@Serializable
data class UserDto(
    val name: String,
    val lastname: String,
    val email: String,
    val uid: String,
    val langCode: String = "en",
    val token: String = "",
) {
    constructor() : this("", "", "", "", "")

    companion object {
        fun fromDomain(user: UserModel): UserDto {
            return UserDto(
                name = user.name,
                lastname = user.lastname,
                langCode = user.langCode,
                email = user.email,
                uid = user.uid,
                token = user.token,
            )
        }
    }
}

fun UserModel.toUserModelDto(): UserDto = UserDto(
    name = name,
    lastname = lastname,
    langCode = langCode,
    email = email,
    uid = uid,
    token = token,
)

fun UserDto.toUserModel(): UserModel = UserModel(
    name = name,
    lastname = lastname,
    langCode = langCode,
    email = email,
    uid = uid,
    token = token,
)