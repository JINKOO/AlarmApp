package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.model.reminder.Reminder

interface GetReminderUseCase {
    suspend fun execute(reminderId: Int): Result<Reminder>
}