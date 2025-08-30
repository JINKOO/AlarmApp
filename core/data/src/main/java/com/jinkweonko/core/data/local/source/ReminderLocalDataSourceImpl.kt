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

    override suspend fun updateReminder(reminderEntity: ReminderEntity) {
        reminderDao.update(reminderEntity)
    }

    override suspend fun getReminder(reminderId: Int): ReminderEntity? =
        reminderDao.getReminder(reminderId)

    override fun getAllReminders(): Flow<List<ReminderEntity>> = reminderDao.getAllReminders()
}