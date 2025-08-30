package com.jinkweonko.core.domain.model

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.core.model.reminder.Reminder

// mapper
fun Reminder.toEntity() = ReminderEntity(
    id = id,
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