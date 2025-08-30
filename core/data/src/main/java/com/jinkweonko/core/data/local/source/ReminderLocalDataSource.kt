package com.jinkweonko.core.data.local.source

import com.jinkweonko.core.data.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderLocalDataSource {
    suspend fun insertReminder(reminderEntity: ReminderEntity)
    suspend fun updateReminder(reminderEntity: ReminderEntity)
    suspend fun getReminder(reminderId: Int): ReminderEntity?
    fun getAllReminders(): Flow<List<ReminderEntity>>
}