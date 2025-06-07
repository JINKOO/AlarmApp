package com.jinkweonko.util.extension

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun LocalDateTime.toTimeFormat(): String {
    val formatter = DateTimeFormatter.ofPattern("a hh:mm", Locale.getDefault())
    return this.format(formatter)
}

fun LocalDateTime.toMillis(): Long {
    return this.withSecond(0).withNano(0).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
}