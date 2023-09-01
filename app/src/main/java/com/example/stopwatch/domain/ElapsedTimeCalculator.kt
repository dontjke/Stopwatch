package com.example.stopwatch.domain

import com.example.stopwatch.data.StopwatchState

class ElapsedTimeCalculator (
    private val timestampProvider: TimeStampProvider
) {
    fun calculate(state: StopwatchState.Running): Long {
        val currentTimestamp = timestampProvider.getMilliseconds()
        val timePassedSinceStart = if (currentTimestamp > state.startTime){
            currentTimestamp - state.startTime
        } else{
            0
        }
        return timePassedSinceStart + state.elapsedTime
    }
}
