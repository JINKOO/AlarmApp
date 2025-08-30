package com.jinkweonko.alarm.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinkweonko.alarm.MyAlarmManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val alarmManager: MyAlarmManager
): ViewModel() {
    
    fun dismissAlarm(reminderId: Int) {
        alarmManager.cancelReminder(reminderId)
    }

    private fun getCurrentAlarm() = viewModelScope.launch {

    }
}