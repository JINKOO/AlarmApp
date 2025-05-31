package com.jinkweonko.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val time: LocalDateTime,
    val ringtone: String
)
