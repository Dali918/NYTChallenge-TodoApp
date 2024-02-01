package com.example.nytchallenge.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nytchallenge.TaskApplication
import com.example.nytchallenge.ui.home.HomeViewModel
import com.example.nytchallenge.ui.task.TaskEditViewModel
import com.example.nytchallenge.ui.task.TaskEntryViewModel

/**
 * Provide instances of ViewModel for different screens
 */

object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(inventoryApplication().container.taskRepository)
        }
        // Initializer for TaskEntryViewModel
        initializer {
            TaskEntryViewModel(inventoryApplication().container.taskRepository)
        }
        //Initializer for TaskEditViewModel
        initializer {
            TaskEditViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.taskRepository
            )

        }
    }
}
/**
 * Extension function to queries for [TaskApplication] object and returns an instance of
 * [TaskApplication].
 */
fun CreationExtras.inventoryApplication(): TaskApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication)