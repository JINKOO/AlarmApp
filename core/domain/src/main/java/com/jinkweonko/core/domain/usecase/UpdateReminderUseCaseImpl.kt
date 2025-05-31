package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.data.repository.ReminderRepository
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.domain.model.toEntity
import javax.inject.Inject

class UpdateReminderUseCaseImpl @Inject constructor(
    private val reminderRepository: ReminderRepository
) : UpdateReminderUseCase {
    override suspend fun updateReminder(reminder: Reminder) {
        reminderRepository.updateReinder(reminder.toEntity())
    }
}