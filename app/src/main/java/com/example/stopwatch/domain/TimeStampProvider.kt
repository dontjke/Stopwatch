package com.example.stopwatch.domain

interface TimeStampProvider {
    fun getMilliseconds(): Long
}