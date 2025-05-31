package com.jinkweonko.core.domain.model

import com.jinkweonko.core.data.entity.ReminderEntity
import java.time.LocalDateTime

data class Reminder(
    val id: Int = 0,
    val name: String = "",
    val time: LocalDateTime = LocalDateTime.now(),
    val ringtone: String = ""
)

// mapper
fun Reminder.toEntity() = ReminderEntity(
    id = id,
    name = name,
    time = time,
    ringtone = ringtone
)