package com.example.nytchallenge

import android.app.Application
import com.example.nytchallenge.data.AppContainer
import com.example.nytchallenge.data.AppDataContainer

class TaskApplication: Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}