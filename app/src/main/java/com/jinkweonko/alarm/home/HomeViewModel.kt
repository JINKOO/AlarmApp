package com.jinkweonko.alarm.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.domain.usecase.GetAllReminderUseCase
import com.jinkweonko.core.domain.usecase.UpdateReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllReminderUseCase: GetAllReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase
): ViewModel() {

    val reminders: StateFlow<List<Reminder>> = getAllReminderUseCase.getAllReminder()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun updateActiveState(
        reminder: Reminder,
        activeState: Boolean
    ) {
        viewModelScope.launch {
            updateReminderUseCase.updateReminder(reminder.copy(isActive = activeState))
        }
    }
}