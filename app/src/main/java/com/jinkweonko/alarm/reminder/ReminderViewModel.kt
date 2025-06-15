package com.jinkweonko.alarm.reminder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinkweonko.alarm.MyAlarmManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val alarmManager: MyAlarmManager
): ViewModel() {

    private val reminderId = savedStateHandle.get<Int>("reminderId") ?: -1

    fun setReminderId(reminderId: String) {
        savedStateHandle["reminderId"] = reminderId
    }

    suspend fun dismissAlarm(reminderId: Int) {

    }

    private fun getCurrentAlarm() = viewModelScope.launch {

    }
}