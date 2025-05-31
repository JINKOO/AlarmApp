package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.domain.model.Reminder

interface UpdateReminderUseCase {
    suspend fun updateReminder(reminder: Reminder)
}