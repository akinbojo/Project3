package com.example.project3

import android.annotation.SuppressLint


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {

    private lateinit var scoreTextView: TextView
    private lateinit var restartButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        scoreTextView = findViewById(R.id.scoreTextView)
        restartButton = findViewById(R.id.restartButton)

        val score = intent.getStringExtra("score") ?: "0 out of 0"
        scoreTextView.text = "Your score: $score"

        restartButton.setOnClickListener {
            // Navigate back to the start screen when "Do it again" is clicked
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}