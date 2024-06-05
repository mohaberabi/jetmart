package com.mohaberabi.jetmart.core.util.extensions

import kotlin.enums.enumEntries


@OptIn(ExperimentalStdlibApi::class)
inline fun <reified T : Enum<T>> String.toEnum(ignoreCase: Boolean = true): T? {
    return enumEntries<T>().find { it.name.equals(this, ignoreCase = ignoreCase) }
}