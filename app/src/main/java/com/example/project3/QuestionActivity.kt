package com.example.project3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlin.random.Random

class QuestionActivity : AppCompatActivity() {

    private lateinit var questionTextView: TextView
    private lateinit var questionExpressionTextView: TextView
    private lateinit var answerEditText: EditText
    private lateinit var doneButton: Button
    private lateinit var imageView: ImageView

    private var numberOfQuestions = 0
    private var currentQuestion = 0
    private var correctAnswers = 0
    private lateinit var operation: String
    private lateinit var difficulty: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_question)

        questionTextView = findViewById(R.id.questionTextView)
        questionExpressionTextView = findViewById(R.id.questionExpressionTextView)
        answerEditText = findViewById(R.id.answerEditText)
        doneButton = findViewById(R.id.doneButton)
        imageView = findViewById(R.id.imageView)

        // Retrieve the selected difficulty, operation, and number of questions from the intent
        difficulty = intent.getStringExtra("difficulty") ?: "Easy"
        operation = intent.getStringExtra("operation") ?: "Addition"
        numberOfQuestions = intent.getIntExtra("numQuestions", 10)

        doneButton.setOnClickListener {
            // Check the user's answer when the "DONE" button is clicked
            checkAnswer()
        }

        // Generate the first question when the activity starts
        generateQuestion()
    }

    private fun generateQuestion() {
        if (currentQuestion < numberOfQuestions) {
            val num1: Int
            val num2: Int

            when (difficulty) {
                "Easy" -> {
                    num1 = Random.nextInt(1, 10) // Operand below 10 for easy
                    num2 = Random.nextInt(1, 10)
                }
                "Medium" -> {
                    num1 = Random.nextInt(11, 25) // Operand less than 25 for medium
                    num2 = Random.nextInt(11, 25)
                }
                "Hard" -> {
                    num1 = Random.nextInt(26, 50) // Operand less than 50 for hard
                    num2 = Random.nextInt(26, 50)
                }
                else -> {
                    num1 = 1
                    num2 = 1
                }
            }

            val question = when (operation) {
                "Addition" -> "$num1 + $num2"
                "Multiplication" -> "$num1 * $num2"
                "Division" -> "$num1 / $num2"
                "Subtraction" -> "$num1 - $num2"
                else -> ""
            }

            questionExpressionTextView.text = question
            answerEditText.text.clear()
            currentQuestion++
        } else {
            // All questions are answered, navigate to the result screen
            val score = "$correctAnswers out of $numberOfQuestions"
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
            finish()
        }
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText.text.toString().trim()

        if (userAnswer.isNotEmpty()) {
            val question = questionExpressionTextView.text.toString()
            val correctAnswer = evaluateExpression(question)

            if (userAnswer == correctAnswer.toString()) {
                correctAnswers++
            }

            generateQuestion()
            if (currentQuestion > numberOfQuestions) {
                navigateToResultActivity() // Navigate to ResultActivity
            }

        }
    }

    private fun evaluateExpression(expression: String): Int {
        // Split the expression into operands and operator
        val parts = expression.split(" ")
        if (parts.size != 3) {
            return 0 // Invalid expression
        }

        val num1 = parts[0].toIntOrNull() ?: return 0
        val operator = parts[1]
        val num2 = parts[2].toIntOrNull() ?: return 0

        // Evaluate the expression based on the operator
        return when (operator) {
            "+" -> num1 + num2
            "*" -> num1 * num2
            "/" -> if (num2 != 0) num1 / num2 else 0 // Handle division by zero
            "-" -> num1 - num2
            else -> 0 // Invalid operator
        }
    }

    private fun navigateToResultActivity() {
        val score = "$correctAnswers out of $numberOfQuestions"
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra("score", score)
        startActivity(intent)
        finish()
    }
}