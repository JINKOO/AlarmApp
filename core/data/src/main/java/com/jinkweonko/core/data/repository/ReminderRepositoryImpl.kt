package com.jinkweonko.core.data.repository

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.core.data.local.source.ReminderLocalDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val localDataSource: ReminderLocalDataSource,
    private val coroutineScope: CoroutineScope
) : ReminderRepository {
    override suspend fun insertReminder(reminderEntity: ReminderEntity) {
        coroutineScope.launch {
            localDataSource.insertReminder(reminderEntity)
        }
    }

    override suspend fun updateReinder(reminderEntity: ReminderEntity) {
        localDataSource.updateReminder(reminderEntity)
    }

    override fun getAllReminders(): Flow<List<ReminderEntity>> = localDataSource.getAllReminders()
}