package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.data.repository.ReminderRepository
import com.jinkweonko.core.domain.model.toModel
import com.jinkweonko.core.model.reminder.Reminder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllReminderUseCaseImpl @Inject constructor(
    private val reminderRepository: ReminderRepository
) : GetAllReminderUseCase {
    override fun getAllReminder(): Flow<List<Reminder>> =
        reminderRepository.getAllReminders()
            .map { entityList ->
                entityList.map { it.toModel() }
            }

}