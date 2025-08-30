package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.data.repository.ReminderRepository
import com.jinkweonko.core.domain.model.toEntity
import com.jinkweonko.core.model.reminder.Reminder
import javax.inject.Inject

class InsertReminderUseCaseImpl @Inject constructor(
    private val reminderRepository: ReminderRepository,
) : InsertReminderUseCase {
    override suspend fun insertReminder(reminder: Reminder) {
        reminderRepository.insertReminder(reminder.toEntity())
    }
}