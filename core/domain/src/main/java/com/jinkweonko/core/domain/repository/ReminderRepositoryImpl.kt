package com.jinkweonko.core.domain.repository

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.core.data.local.source.ReminderLocalDataSource
import com.jinkweonko.core.data.repository.ReminderRepository
import com.jinkweonko.core.domain.model.toModel
import com.jinkweonko.core.model.reminder.Reminder
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val localDataSource: ReminderLocalDataSource
) : ReminderRepository {
    override suspend fun insertReminder(reminderEntity: ReminderEntity) {
        localDataSource.insertReminder(reminderEntity)
    }

    override suspend fun updateReinder(reminderEntity: ReminderEntity) {
        localDataSource.updateReminder(reminderEntity)
    }

    override suspend fun getReminder(reminderId: Int): Result<Reminder> = runCatching {
        val result = localDataSource.getReminder(reminderId)
        result?.toModel() ?: throw Exception("Reminder not found with ID: $reminderId")
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> = localDataSource.getAllReminders()
}