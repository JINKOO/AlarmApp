package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.model.reminder.Reminder

interface UpdateReminderUseCase {
    suspend fun updateReminder(reminder: Reminder)
}