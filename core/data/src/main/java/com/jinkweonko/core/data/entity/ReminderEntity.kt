package com.jinkweonko.core.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val time: LocalDateTime,
    val ringtone: String,
    val isActive: Boolean
)
