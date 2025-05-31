package com.jinkweonko.core.data.local.source

import com.jinkweonko.core.data.entity.ReminderEntity
import com.jinkweonko.core.data.local.database.ReminderDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ReminderLocalDataSourceImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderLocalDataSource {
    override suspend fun insertReminder(reminderEntity: ReminderEntity) {
        reminderDao.insert(reminderEntity)
    }

    override fun getAllReminders(): Flow<ReminderEntity> {
        TODO("Not yet implemented")
    }
}