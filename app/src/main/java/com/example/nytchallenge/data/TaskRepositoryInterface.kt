package com.example.nytchallenge.data

import kotlinx.coroutines.flow.Flow

interface TaskRepositoryInterface {
    /**
     * Get All Tasks from the data source
     */
    fun getAllTasks(): Flow<List<TaskEntity>>

    /**
     * Get a single task from the data source
     */
    fun getTask(id: Int): Flow<TaskEntity>

    /**
     * Insert a task into the data source
     */
    suspend fun insertTask(task: TaskEntity)

    /**
     * Update a task in the data source
     */
    suspend fun updateTask(task: TaskEntity)

    /**
     * Delete a task from the data source
     */
    suspend fun deleteTask(task: TaskEntity)
}