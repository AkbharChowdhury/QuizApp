package com.quiz.quizapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btnStartQuiz = findViewById(R.id.btn_start_quiz);
        btnStartQuiz.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), QuizActivity.class)));
    }
}