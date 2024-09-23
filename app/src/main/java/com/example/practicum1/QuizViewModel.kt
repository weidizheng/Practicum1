package com.example.practicum1

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class QuizViewModel(private val state: SavedStateHandle) : ViewModel() {

    private val questionBank = listOf(
        Question(R.string.question_1, true),
        Question(R.string.question_2, false),
        Question(R.string.question_3, true),
        Question(R.string.question_4, false),
        Question(R.string.question_5, true),
        Question(R.string.question_6, false)
    )

    var currentIndex: Int
        get() = state.get<Int>("currentIndex") ?: 0
        set(value) = state.set("currentIndex", value)

    var isCheater: Boolean
        get() = state.get<Boolean>("isCheater") ?: false
        set(value) = state.set("isCheater", value)

    val currentQuestionText: Int
        get() = questionBank[currentIndex].text

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val questionBankSize: Int
        get() = questionBank.size

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrevious() {
        if (currentIndex > 0) {
            currentIndex = (currentIndex - 1) % questionBank.size
        }
    }

    fun setCheaterStatus(cheater: Boolean) {
        isCheater = cheater
    }
}
