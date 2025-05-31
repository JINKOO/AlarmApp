package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.domain.model.Reminder

interface InsertReminderUseCase {
    suspend fun insertReminder(reminder: Reminder)
}