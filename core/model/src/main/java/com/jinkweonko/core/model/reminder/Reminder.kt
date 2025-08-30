package com.jinkweonko.core.model.reminder

import java.time.LocalDateTime

data class Reminder(
    val id: Int = 0,
    val title: String = "",
    val time: LocalDateTime = LocalDateTime.now(),
    val ringtone: String = "",
    val isActive: Boolean = false
) {
    val amPm: String
        get() = if(time.hour < 12) "AM" else "PM"
}