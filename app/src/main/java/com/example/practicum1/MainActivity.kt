package com.example.practicum1
import com.google.android.material.snackbar.Snackbar
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practicum1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
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

        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt("currentIndex")
        }

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
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        binding.previousButton.setOnClickListener {
            currentIndex = if (currentIndex > 0) currentIndex - 1 else questionBank.size - 1
            updateQuestion()
            binding.trueButton.isEnabled = true
            binding.falseButton.isEnabled = true
        }

        binding.questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("currentIndex", currentIndex)
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

        binding.trueButton.isEnabled = false
        binding.falseButton.isEnabled = false

        if (currentIndex == questionBank.size - 1) {
            val scorePercentage = (score.toDouble() / questionBank.size) * 100
            Toast.makeText(this, "Quiz Score: $scorePercentage%", Toast.LENGTH_SHORT).show()
        }
    }
}
