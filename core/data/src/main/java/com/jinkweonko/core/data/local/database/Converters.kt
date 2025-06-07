package com.jinkweonko.core.data.local.database

import androidx.room.TypeConverter
import com.jinkweonko.util.extension.toMillis
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

class Converters {
    @TypeConverter
    fun toLocalDateTime(value: Long): LocalDateTime? {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneId.systemDefault())
    }

    @TypeConverter
    fun fromLocalDateTime(localDateTime: LocalDateTime): Long? {
        return localDateTime.toMillis()
    }
}