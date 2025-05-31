package com.jinkweonko.core.data.repository

import com.jinkweonko.core.data.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun insertReminder(reminderEntity: ReminderEntity)
    fun getAllReminders(): Flow<ReminderEntity>
}