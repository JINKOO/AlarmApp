package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.model.reminder.Reminder
import kotlinx.coroutines.flow.Flow

interface GetAllReminderUseCase {
    fun getAllReminder(): Flow<List<Reminder>>
}