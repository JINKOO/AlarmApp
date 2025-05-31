package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.domain.model.Reminder
import kotlinx.coroutines.flow.Flow

interface GetAllReminderUseCase {
    fun getAllReminder(): Flow<List<Reminder>>
}