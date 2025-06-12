package com.jinkweonko.alarm.detail

import android.app.AlarmManager
import androidx.compose.material3.TimePickerState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jinkweonko.alarm.MyAlarmManager
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.domain.usecase.InsertReminderUseCase
import com.jinkweonko.core.domain.usecase.UpdateReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = false,
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val alarmManager: MyAlarmManager,
    private val insertReminderUseCase: InsertReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase
) : ViewModel() {

    val reminderId: String = savedStateHandle.get<String>("reminderId").orEmpty()

    init {
        getReminder()
    }

    private fun getReminder() = viewModelScope.launch {
        if (reminderId.isNotEmpty()) return@launch
    }

    fun addReminder(
        title: String,
        hour: Int,
        minute: Int,
        ringtoneTitle: String = "",
    ) = viewModelScope.launch {
        val reminder = Reminder(
            title = title,
            time = LocalDateTime.now().withHour(hour).withMinute(minute),
            ringtone = ringtoneTitle,
            isActive = true
        )
        launch { insertReminder(reminder) }
        launch { setReminder(reminder) }
    }

    private suspend fun insertReminder(reminder: Reminder) {
        insertReminderUseCase.insertReminder(reminder)
    }

    private fun setReminder(reminder: Reminder) {
        alarmManager.setReminder(reminder)
    }


    fun updateReminder() {

    }
}