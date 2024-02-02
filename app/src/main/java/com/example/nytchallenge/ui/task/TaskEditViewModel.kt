package com.example.nytchallenge.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytchallenge.data.TaskRepository
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class TaskEditViewModel(
    savedStateHandle: SavedStateHandle,
    private val taskRepository: TaskRepository
): ViewModel() {
    /**
     * Holds current task ui state
     */
    var uiState by mutableStateOf(TaskUiState())
        private set

    private val taskId: Int = checkNotNull(savedStateHandle[TaskEditDestination.taskIdArg])

    /**
     * Initializes the taskUiState with the task id provided when launching the screen .
     */
    init {
        viewModelScope.launch{
            uiState = taskRepository.getTaskStream(taskId)
                .filterNotNull()
                .first()
                .toTaskUiState()
        }
    }
    /**
     * Updates the [uiState] with the value provided in the argument.
     */
    fun updateUiState(taskDetails: TaskDetails) {
        uiState = TaskUiState(
            taskDetails = taskDetails,
            isDone = taskDetails.isDone
        )
    }

    /**
     * Updates the task in the Room Database.
     */
    suspend fun updateTask(){
        taskRepository.updateTask(uiState.taskDetails.toTaskEntity())
    }

}