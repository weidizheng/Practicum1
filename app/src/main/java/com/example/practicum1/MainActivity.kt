package com.example.practicum1

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateQuestion()

        binding.trueButton.setOnClickListener {
            checkAnswer(true)
        }

        binding.falseButton.setOnClickListener {
            checkAnswer(false)
        }

        binding.nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        binding.previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        binding.questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        Log.d(TAG, "onCreate called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val message = if (userAnswer == correctAnswer) {
            score++
            "Correct!"
        } else {
            "Incorrect!"
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()

        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

        if (quizViewModel.currentIndex == questionBank.size - 1) {
            val scorePercentage = (score.toDouble() / questionBank.size) * 100
            Toast.makeText(this, "Quiz Score: $scorePercentage%", Toast.LENGTH_SHORT).show()
        }
    }
}
