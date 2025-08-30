package com.jinkweonko.core.data.repository

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.core.model.reminder.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    suspend fun insertReminder(reminderEntity: ReminderEntity)
    suspend fun updateReinder(reminderEntity: ReminderEntity)
    suspend fun getReminder(reminderId: Int): Result<Reminder>
    fun getAllReminders(): Flow<List<ReminderEntity>>
}