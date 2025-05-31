package com.jinkweonko.core.domain.usecase

import com.jinkweonko.core.data.MyAlarmManager
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.domain.model.toEntity
import javax.inject.Inject

class InsertReminderUseCaseImpl @Inject constructor(
    private val alarmManager: MyAlarmManager
) : InsertReminderUseCase {
    override suspend fun insertReminder(reminder: Reminder) {
        // TODO Room에 insert 및 AlarmManager에서 Setting하기
        alarmManager.setReminder(reminder.toEntity())
    }
}