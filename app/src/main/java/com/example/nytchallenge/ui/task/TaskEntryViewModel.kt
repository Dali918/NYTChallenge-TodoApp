package com.example.nytchallenge.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.nytchallenge.data.TaskEntity
import com.example.nytchallenge.data.TaskRepository
/**
 * A [ViewModel] to insert items into the room database
 */
class TaskEntryViewModel(private val taskRepository: TaskRepository) : ViewModel() {
    var taskUiState by mutableStateOf(TaskUiState())
        private set

    /**
     * Updates the [taskUiState] with the value provided in the argument.
     */

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState = TaskUiState(
            taskDetails = taskDetails,
            isDone = taskDetails.isDone
        )
    }

    //save to database after entry function
    suspend fun saveTask() {
        taskRepository.insertTask(taskUiState.taskDetails.toTaskEntity())
    }

}

/**
 * A view state for [TaskEntryViewModel]
 */

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isDone: Boolean = false
)

data class TaskDetails(
    val id: Int = 0,
    val task: String = "",
    val isDone: Boolean = false
)


/**
 * Extension function to convert [TaskDetails] to [TaskEntity]
 */
fun TaskDetails.toTaskEntity(): TaskEntity = TaskEntity(
    id = id,
    task = task,
    isDone = isDone
)

/**
 * Extension function to convert [TaskEntity] to [TaskDetails]
 */
fun TaskEntity.toTaskDetails(): TaskDetails = TaskDetails(
    id = id,
    task = task,
    isDone = isDone
)

/**
 * Extension function to convert [TaskEntity] to [TaskUiState]
 */

fun TaskEntity.toTaskUiState(): TaskUiState = TaskUiState(
    taskDetails = this.toTaskDetails(),
    isDone = isDone
)
