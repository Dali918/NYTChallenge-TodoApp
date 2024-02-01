package com.example.nytchallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytchallenge.data.TaskEntity
import com.example.nytchallenge.data.TaskRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * HomeViewModel is to get all items in database
 */
class HomeViewModel(taskRepository: TaskRepository): ViewModel() {
    val homeUiState: StateFlow<HomeUiState> = taskRepository.getAllTasksStream().map {
        HomeUiState(it)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
        initialValue = HomeUiState()
    )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

}

/**
 * Ui state for [HomeViewModel]
 */
data class HomeUiState(val itemList :List<TaskEntity> = listOf())