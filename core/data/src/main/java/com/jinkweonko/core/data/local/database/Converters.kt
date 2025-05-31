package com.jinkweonko.core.data.local.database

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun fromLocalDateTime(value: Long): LocalDateTime? {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(value), ZoneId.systemDefault())
    }

    @TypeConverter
    fun localDateTimeToLong(localDateTime: LocalDateTime): Long? {
        return localDateTime.atZone(ZoneId.systemDefault()).toEpochSecond()
    }
}