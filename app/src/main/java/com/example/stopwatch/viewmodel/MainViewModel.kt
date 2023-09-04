package com.example.stopwatch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.stopwatch.domain.ElapsedTimeCalculator
import com.example.stopwatch.domain.StopwatchListOrchestrator
import com.example.stopwatch.domain.StopwatchStateCalculator
import com.example.stopwatch.domain.StopwatchStateHolder
import com.example.stopwatch.domain.TimeStampMillisecondsFormatter
import com.example.stopwatch.domain.TimeStampProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val liveData: MutableLiveData<String> = MutableLiveData()

    private val timestampProvider = object : TimeStampProvider {
        override fun getMilliseconds(): Long {
            return System.currentTimeMillis()
        }
    }

    private val stopwatchListOrchestrator = StopwatchListOrchestrator(
        StopwatchStateHolder(
            StopwatchStateCalculator(
                timestampProvider,
                ElapsedTimeCalculator(timestampProvider)
            ),
            ElapsedTimeCalculator(timestampProvider),
            TimeStampMillisecondsFormatter()
        ),
        CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        )
    )

    fun getLiveData(): LiveData<String> = liveData

    fun getData() {
        CoroutineScope(
            Dispatchers.Main + SupervisorJob()
        ).launch {
            stopwatchListOrchestrator.ticker.collect {
                liveData.value = it
            }
        }
    }

    fun start() {
        stopwatchListOrchestrator.start()
    }

    fun pause() {
        stopwatchListOrchestrator.pause()
    }

    fun stop() {
        stopwatchListOrchestrator.stop()
    }
}