package com.example.nytchallenge.data

import kotlinx.coroutines.flow.Flow

class TasksOfflineRepository(private val taskDao: TaskDao): TaskRepository {
    override fun getAllTasksStream(): Flow<List<TaskEntity>> = taskDao.getAllTasks()
    override fun getTaskStream(id: Int): Flow<TaskEntity?> = taskDao.getTask(id)
    override suspend fun insertTask(task: TaskEntity) = taskDao.insert(task)
    override suspend fun updateTask(task: TaskEntity) = taskDao.update(task)
    override suspend fun deleteTask(task: TaskEntity) = taskDao.delete(task)
}