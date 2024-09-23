package com.example.practicum1

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val questionBank = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false)
    )

    private var currentIndex = 0
    private var score = 0
    private lateinit var binding: ActivityMainBinding

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
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        binding.previousButton.setOnClickListener {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else questionBank.size - 1
            updateQuestion()
        }

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }
    }

    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        binding.questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer
        val message = if (userAnswer == correctAnswer) {
            score++
            "Correct!"
        } else {
            "Incorrect!"
        }
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }
}
