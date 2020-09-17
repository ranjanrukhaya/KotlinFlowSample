package com.gaura.learn.kotlinflowsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    lateinit var flow: Flow<Int>
    lateinit var flowOne: Flow<String>
    lateinit var flowTwo: Flow<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupFlow()
        setupClicks()
    }

    private fun setupFlow() {
        flow = flow {
            Log.d(TAG, "Start flow")
            (0..10).forEach {
                // Emit items with 500 milliseconds delay
                delay(500)
                Log.d(TAG, "Emitting $it")
                emit(it)

            }
        }.flowOn(Dispatchers.Default)

        flowOne = flowOf("Virat", "Rohit", "Yuvraj").flowOn(Dispatchers.Default)
        flowTwo = flowOf("Kohli", "Sharma", "Singh").flowOn(Dispatchers.Default)

    }

    private fun setupClicks() {
        button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flow.collect {
                    Log.d(TAG, "${it.toString()}")
                }
            }
        }

        zip_button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                flowOne.zip(flowTwo)
                { firstString, secondString ->
                    "$firstString $secondString"
                }.collect {
                    Log.d(TAG, it)
                }
            }
        }
    }

}