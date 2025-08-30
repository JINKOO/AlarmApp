package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.data.repository.ReminderRepository
import com.jinkweonko.core.model.reminder.Reminder
import javax.inject.Inject

class GetReminderUseCaseImpl @Inject constructor(
    private val repository: ReminderRepository
) : GetReminderUseCase {
    override suspend fun execute(reminderId: Int): Result<Reminder> =
        repository.getReminder(reminderId)
}