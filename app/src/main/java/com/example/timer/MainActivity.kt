package com.example.timer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private var mlSeconds: Int = 0
    private var isRunning: Boolean = false
    private var wasRunning = false


    private fun startTimer(v: View) {
        isRunning = true

    }

    private fun pauseTimer(v: View) {
        isRunning = false
    }

    private fun resetTimer(v: View) {
        isRunning = false
        mlSeconds = 0

    }

    private fun runTimer() {

        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {
                var secs: Int = mlSeconds / 10
                var msecs: Int = mlSeconds % 10
                var mins: Int = (secs % 3600) / 60
                var hours: Int = secs / 3600

                var time = String.format("%02d:%02d:%02d.%1d", hours, mins, secs, msecs)

                textViewTimer.text = time

                if (isRunning) mlSeconds++
                handler.postDelayed(this, 100)
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonStart.setOnClickListener(this::startTimer)
        buttonPause.setOnClickListener(this::pauseTimer)
        buttonReset.setOnClickListener(this::resetTimer)

        mlSeconds = savedInstanceState?.getInt("mlsecs") ?: 0
        isRunning = savedInstanceState?.getBoolean("running") ?: false
        wasRunning = savedInstanceState?.getBoolean("wasRunning") ?: false

        runTimer()
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putInt("mlsecs", mlSeconds)
        outState?.putBoolean("running", isRunning)
        outState?.putBoolean("wasRunning", wasRunning)
    }


    override fun onPause() {
        wasRunning = isRunning
        isRunning = false
        super.onPause()
    }


    override fun onResume() {
        isRunning = wasRunning
        super.onResume()
    }
}
