package com.example.nytchallenge.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytchallenge.data.TaskEntity
import com.example.nytchallenge.data.TaskRepository
import com.example.nytchallenge.ui.task.TaskDetails
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * HomeViewModel is to get all items in database
 */
class HomeViewModel(private val taskRepository: TaskRepository): ViewModel() {


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

    suspend fun onTaskCheckedChange(taskEntity: TaskEntity) {
        taskRepository.updateTask(taskEntity)
    }

}



/**
 * Ui state for [HomeViewModel]
 */
data class HomeUiState(val tasksList :List<TaskEntity> = listOf())