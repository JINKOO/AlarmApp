package com.jinkweonko.core.data.repository

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.core.data.local.source.ReminderLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val localDataSource: ReminderLocalDataSource
) : ReminderRepository {
    override suspend fun insertReminder(reminderEntity: ReminderEntity) {
        localDataSource.insertReminder(reminderEntity)
    }

    override fun getAllReminders(): Flow<ReminderEntity> = localDataSource.getAllReminders()
}