package com.example.stopwatch.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        viewModel.getLiveData().observe(
            this@MainActivity
        ) { renderData(it) }

        viewModel.getData()

        binding.buttonStart.setOnClickListener {
            viewModel.start()
        }

        binding.buttonPause.setOnClickListener {
            viewModel.pause()
        }

        binding.buttonStop.setOnClickListener {
            viewModel.stop()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun renderData(data: String) {
        binding.textTime.text = data
    }
}