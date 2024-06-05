package com.mohaberabi.jetmart.core.util.extensions

import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter


fun ZonedDateTime.appDefaultFormat(): String {

    val formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a")
    val formattedDateTime = format(formatter)
    return formattedDateTime
}