package com.jinkweonko.core.data.local.source

import com.jinkweonko.core.data.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderLocalDataSource {
    suspend fun insertReminder(reminderEntity: ReminderEntity)
    fun getAllReminders(): Flow<ReminderEntity>
}