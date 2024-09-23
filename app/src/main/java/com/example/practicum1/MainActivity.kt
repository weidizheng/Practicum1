package com.example.practicum1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private val quizViewModel: QuizViewModel by viewModels()
    private var score = 0
    private var isCheater = false

    private val cheatLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            isCheater = result.data?.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false) ?: false
            quizViewModel.setCheaterStatus(isCheater)
            if (isCheater) {
                binding.cheatButton.isEnabled = false
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateQuestion()

        binding.trueButton.setOnClickListener { checkAnswer(true) }
        binding.falseButton.setOnClickListener { checkAnswer(false) }
        binding.nextButton.setOnClickListener { nextQuestion() }
        binding.previousButton.setOnClickListener { previousQuestion() }
        binding.cheatButton.setOnClickListener { startCheatActivity() }
        binding.questionTextView.setOnClickListener { nextQuestion() }

        if (quizViewModel.isCheater) {
            binding.cheatButton.isEnabled = false
        }

        Log.d(TAG, "onCreate called")
    }

    private fun updateQuestion() {
        val questionTextResId = quizViewModel.currentQuestionText
        binding.questionTextView.setText(questionTextResId)
        isCheater = false
        binding.cheatButton.isEnabled = !quizViewModel.isCheater
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val message = when {
            quizViewModel.isCheater -> "Cheating is wrong!"
            userAnswer == correctAnswer -> {
                score++
                "Correct!"
            }
            else -> "Incorrect!"
        }

        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        setAnswerButtonsEnabled(false)

        if (quizViewModel.currentIndex == quizViewModel.questionBankSize - 1) {
            val scorePercentage = (score.toDouble() / quizViewModel.questionBankSize) * 100
            Toast.makeText(this, "Quiz Score: $scorePercentage%", Toast.LENGTH_SHORT).show()
            Log.d(TAG, "Final score: $scorePercentage%")
        }

        Log.d(TAG, "Checked answer: $userAnswer, Correct: $correctAnswer")
    }

    private fun nextQuestion() {
        quizViewModel.moveToNext()
        updateQuestion()
        setAnswerButtonsEnabled(true)
        Log.d(TAG, "Moved to next question, index: ${quizViewModel.currentIndex}")
    }

    private fun previousQuestion() {
        quizViewModel.moveToPrevious()
        updateQuestion()
        setAnswerButtonsEnabled(true)
        Log.d(TAG, "Moved to previous question, index: ${quizViewModel.currentIndex}")
    }

    private fun startCheatActivity() {
        val intent = CheatActivity.newIntent(this, quizViewModel.currentQuestionAnswer)
        cheatLauncher.launch(intent)
    }

    private fun setAnswerButtonsEnabled(enabled: Boolean) {
        binding.trueButton.isEnabled = enabled
        binding.falseButton.isEnabled = enabled
    }
}
