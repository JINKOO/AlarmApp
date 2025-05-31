package com.jinkweonko.alarm.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.domain.usecase.InsertReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

data class UiState(
    val isLoading: Boolean,
)

@HiltViewModel
class DetailViewModel @Inject constructor(
    val savedStateHandle: SavedStateHandle,
    private val insertReminderUseCase: InsertReminderUseCase
): ViewModel() {

    val reminderId: String = savedStateHandle.get<String>("reminderId").orEmpty()

    init {
        if (reminderId.isNotEmpty()) {
            getReminder()
        }
    }

    private fun getReminder() = viewModelScope.launch {
        // TODO id로 리마인더 조회
    }

    fun addReminder() = viewModelScope.launch {
        insertReminderUseCase.insertReminder(Reminder())
    }

    fun updateReminder() {

    }
}