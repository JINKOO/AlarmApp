package com.jinkweonko.alarm.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.domain.usecase.InsertReminderUseCase
import com.jinkweonko.core.domain.usecase.UpdateReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean = false,
    val reminder: Reminder = Reminder()
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val insertReminderUseCase: InsertReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase
): ViewModel() {

    val reminderId: String = savedStateHandle.get<String>("reminderId").orEmpty()

    init {
        getReminder()
    }

    private fun getReminder() = viewModelScope.launch {
        if (reminderId.isNotEmpty()) return@launch
    }

    fun addReminder(
        title: String,
        ringtoneTitle: String = "",
    ) = viewModelScope.launch {
        insertReminderUseCase.insertReminder(
            Reminder(
                title = title,
                time = LocalDateTime.now(),
                ringtone = ringtoneTitle,
                isActive = true
            )
        )
    }

    fun updateReminder() {

    }
}