package com.example.nytchallenge.data

import android.content.Context

/**
 * An app container for dependency injection
 */
interface AppContainer{
    val taskRepository: TaskRepository
}

/**
 * [AppContainer] implementation that provides instance of [TasksOfflineRepository]
 */

class AppDataContainer(private val context: Context): AppContainer {
    override val taskRepository: TaskRepository by lazy {
        TasksOfflineRepository(TasksDatabase.getDatabase(context).taskDao())
    }
}
