package com.example.lab6_1_executorservice

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Future

class MainActivity : AppCompatActivity() {
    var secondsElapsed: Int = 0
    lateinit var textSecondsElapsed: TextView
    private lateinit var myApp: MyApplication
    private lateinit var task: Future<*>

    @SuppressLint("SetTextI18n")
    private fun startNewTask(): Future<*> {
        return myApp.executor.submit {
            while (!myApp.executor.isShutdown) {
                Log.d("Thread: ", Thread.currentThread().name)
                Thread.sleep(1000)
                textSecondsElapsed.post {
                    textSecondsElapsed.text = "Seconds elapsed: " + secondsElapsed++
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
        myApp = applicationContext as MyApplication
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        secondsElapsed = savedInstanceState.getInt("seconds")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("seconds", secondsElapsed)
    }

    override fun onStop() {
        super.onStop()
        task.cancel(true)
    }

    override fun onStart() {
        super.onStart()
        task = startNewTask()
    }
}