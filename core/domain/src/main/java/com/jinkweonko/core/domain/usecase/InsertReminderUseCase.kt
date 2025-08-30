package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.model.reminder.Reminder

interface InsertReminderUseCase {
    suspend fun insertReminder(reminder: Reminder)
}