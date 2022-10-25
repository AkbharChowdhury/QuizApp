package com.quiz.quizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.quiz.quizapp.classes.DBHelper;
import com.quiz.quizapp.classes.Question;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.List;

public class QuizActivity extends AppCompatActivity {
    private TextView tvQuestion;
    private TextView tvScore;
    private TextView tvQuestionCount;
    private TextView tvCountdown;
    private RadioGroup rbGroup;
    private RadioButton rb1;
    private RadioButton rb2;
    private RadioButton rb3;
    private List<Question> questionList;
    private Button btnConfirmNext;
    private Context context;
    private ColorStateList textColorDefaultRb;
    private int questionCounter = 0;
    private int questionCountTotal;
    private Question currentQuestion;
    private int score;
    private boolean answered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        tvQuestion = findViewById(R.id.tv_question);
        tvScore = findViewById(R.id.tv_score);
        tvQuestionCount = findViewById(R.id.tv_question_count);
        tvCountdown = findViewById(R.id.tv_countdown);

        rb1 = findViewById(R.id.rb1);
        rb2 = findViewById(R.id.rb2);
        rb3 = findViewById(R.id.rb3);

        btnConfirmNext = findViewById(R.id.btn_confirm_next);
        context = getApplicationContext();

        DBHelper db = new DBHelper(context);
        questionList = db.getAllQuestions();

        textColorDefaultRb = rb1.getTextColors();
        questionCountTotal = questionList.size();

        Collections.shuffle(questionList);
        showNextQuestion();

    }

    private void showNextQuestion() {
        for (Question q : questionList){
            Toast.makeText(context,q.getQuestion(), Toast.LENGTH_LONG).show();
        }
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);
            tvQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            tvQuestionCount.setText(questionCountLabel());
            answered = false;
            btnConfirmNext.setText(getString(R.string.confirm));
        } else {
            finishQuiz();

        }


    }
    private void finishQuiz(){
        finish();
    }

    private String questionCountLabel() {
        return MessageFormat.format("Question: {0}/{1}", questionCounter, questionCountTotal);
    }
}