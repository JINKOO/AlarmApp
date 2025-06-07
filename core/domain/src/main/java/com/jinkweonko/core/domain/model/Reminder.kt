package com.jinkweonko.core.domain.model

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.util.extension.toTimeFormat
import java.time.LocalDateTime

data class Reminder(
    val id: Int = 0,
    val title: String = "",
    val time: LocalDateTime = LocalDateTime.now(),
    val ringtone: String = "",
    val isActive: Boolean = false
) {
    private val timeFormat: String
        get() = time.toTimeFormat()

    val amOrPmText: String
        get() = timeFormat.split(" ")[0]

    val timeFormatText: String
        get() = timeFormat.split(" ")[1]

}

// mapper
fun Reminder.toEntity() = ReminderEntity(
    title = title,
    time = time,
    ringtone = ringtone,
    isActive = isActive
)

fun ReminderEntity.toModel() = Reminder(
    id = id,
    title = title,
    time = time,
    ringtone = ringtone,
    isActive = isActive
)