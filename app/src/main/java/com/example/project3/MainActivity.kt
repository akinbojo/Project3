package com.example.project3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var difficultyRadioGroup: RadioGroup
    private lateinit var operationRadioGroup: RadioGroup
    private lateinit var numQuestionsValueTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        difficultyRadioGroup = findViewById(R.id.difficultyRadioGroup)
        operationRadioGroup = findViewById(R.id.operationRadioGroup)
        numQuestionsValueTextView = findViewById(R.id.numQuestionsValueTextView)

        val minusButton = findViewById<Button>(R.id.minusButton)
        val plusButton = findViewById<Button>(R.id.plusButton)
        val startButton = findViewById<Button>(R.id.startButton)

        minusButton.setOnClickListener {
            decrementNumQuestions()
        }

        plusButton.setOnClickListener {
            incrementNumQuestions()
        }

        startButton.setOnClickListener {
            val selectedDifficultyId = difficultyRadioGroup.checkedRadioButtonId
            val selectedOperationId = operationRadioGroup.checkedRadioButtonId
            val numQuestions = numQuestionsValueTextView.text.toString()

            if (selectedDifficultyId == -1 || selectedOperationId == -1 || numQuestions.isEmpty()) {
                // Display a message if any option is not selected
                Toast.makeText(this, "Please select difficulty, operation, and number of questions.", Toast.LENGTH_SHORT).show()
            } else {
                val selectedDifficulty = findViewById<RadioButton>(selectedDifficultyId)?.text.toString()
                val selectedOperation = findViewById<RadioButton>(selectedOperationId)?.text.toString()

                // Start the QuestionActivity with selected options
                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("difficulty", selectedDifficulty)
                intent.putExtra("operation", selectedOperation)
                intent.putExtra("numQuestions", numQuestions.toInt())
                startActivity(intent)
            }
        }
    }

    private fun decrementNumQuestions() {
        val currentNumQuestions = numQuestionsValueTextView.text.toString().toInt()
        if (currentNumQuestions > 1) {
            numQuestionsValueTextView.text = (currentNumQuestions - 1).toString()
        }
    }

    private fun incrementNumQuestions() {
        val currentNumQuestions = numQuestionsValueTextView.text.toString().toInt()
        numQuestionsValueTextView.text = (currentNumQuestions + 1).toString()
    }
}