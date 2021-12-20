package com.example.lab6_1_executorservice

import android.app.Application
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MyApplication : Application() {
    val executor : ExecutorService = Executors.newSingleThreadExecutor()
}