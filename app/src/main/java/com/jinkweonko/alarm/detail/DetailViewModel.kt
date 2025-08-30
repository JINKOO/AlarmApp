package com.jinkweonko.alarm.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinkweonko.alarm.MyAlarmManager
import com.jinkweonko.core.domain.usecase.GetReminderUseCase
import com.jinkweonko.core.domain.usecase.InsertReminderUseCase
import com.jinkweonko.core.domain.usecase.UpdateReminderUseCase
import com.jinkweonko.core.model.reminder.Reminder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = false,
    val reminder: Reminder = Reminder()
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val alarmManager: MyAlarmManager,
    private val insertReminderUseCase: InsertReminderUseCase,
    private val getReminderUseCase: GetReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase
) : ViewModel() {

    val reminderId: Int = savedStateHandle.get<Int>("alarmId") ?: 0

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        getReminder()
    }

    private fun getReminder() = viewModelScope.launch {
        if (reminderId == 0) return@launch
        getReminderUseCase.execute(reminderId.toInt())
            .onSuccess { reminder ->
                _uiState.update { it.copy(reminder = reminder) }
            }
            .onFailure {
                Timber.e("getReminder ERROR: ${it.message}")
            }
    }

    private suspend fun insertReminder(reminder: Reminder) {
        insertReminderUseCase.insertReminder(reminder)
    }

    private fun setReminder(reminder: Reminder) {
        alarmManager.setReminder(reminder)
    }

    private fun generateSimpleId(): Int {
        return (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
    }

    fun addReminder(
        title: String,
        hour: Int,
        minute: Int,
        ringtoneTitle: String = "",
    ) = viewModelScope.launch(Dispatchers.IO) {
        val reminder = Reminder(
            id = generateSimpleId(),
            title = title,
            time = LocalDateTime.now().withHour(hour).withMinute(minute),
            ringtone = ringtoneTitle,
            isActive = true
        )
        launch { insertReminder(reminder) }
        launch { setReminder(reminder) }
    }

    fun updateReminder(
        title: String,
        hour: Int,
        minute: Int,
        ringtoneTitle: String = ""
    ) = viewModelScope.launch {
        val updateReminder = uiState.value.reminder.copy(
            title = title,
            time = LocalDateTime.now().withHour(hour).withMinute(minute),
            ringtone = ringtoneTitle,
            isActive = true
        )
        updateReminderUseCase.updateReminder(updateReminder)
    }
}