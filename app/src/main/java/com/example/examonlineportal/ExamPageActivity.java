package com.example.examonlineportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class ExamPageActivity extends AppCompatActivity {

    TextView tvExamTitle, tvQuestion;
    RadioGroup radioGroup;
    RadioButton rbOptionA, rbOptionB, rbOptionC, rbOptionD;
    Button btnNext, btnSubmit;

    String[][] questions;
    Map<String, String[][]> subjectWiseQuestions = new HashMap<>();
    int currentQuestion = 0;
    int score = 0;
    String selectedSubject = "General";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);

        tvExamTitle = findViewById(R.id.tvExamTitle);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioGroup = findViewById(R.id.radioGroupOptions);
        rbOptionA = findViewById(R.id.rbOptionA);
        rbOptionB = findViewById(R.id.rbOptionB);
        rbOptionC = findViewById(R.id.rbOptionC);
        rbOptionD = findViewById(R.id.rbOptionD);
        btnNext = findViewById(R.id.btnNext);
        btnSubmit = findViewById(R.id.btnSubmit);

        selectedSubject = getIntent().getStringExtra("exam_name"); // e.g., "Maths", "Science"
        initializeQuestions();
        questions = subjectWiseQuestions.get(selectedSubject);

        if (questions == null || questions.length == 0) {
            Toast.makeText(this, "No questions available for " + selectedSubject, Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        tvExamTitle.setText(selectedSubject + " Exam");

        loadQuestion();

        btnNext.setOnClickListener(v -> {
            if (checkAnswer()) {
                if (currentQuestion < questions.length - 1) {
                    currentQuestion++;
                    loadQuestion();
                } else {
                    btnNext.setVisibility(View.GONE);
                    btnSubmit.setVisibility(View.VISIBLE);
                }
            }
        });

        btnSubmit.setOnClickListener(v -> {
            if (checkAnswer()) {
                Intent intent = new Intent(ExamPageActivity.this, ResultsActivity.class);
                intent.putExtra("score", score);
                intent.putExtra("total", questions.length);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initializeQuestions() {
        subjectWiseQuestions.put("Mathematics", new String[][]{
                {"What is 2 + 2?", "3", "4", "5", "6", "B"},
                {"What is 5 * 6?", "30", "25", "20", "35", "A"},
                {"Square root of 49?", "5", "6", "7", "8", "C"}
        });

        subjectWiseQuestions.put("Science", new String[][]{
                {"What planet is known as the Red Planet?", "Earth", "Mars", "Jupiter", "Venus", "B"},
                {"What gas do plants need for photosynthesis?", "Oxygen", "Carbon Dioxide", "Nitrogen", "Hydrogen", "B"},
                {"Which part of the cell contains DNA?", "Nucleus", "Membrane", "Cytoplasm", "Ribosome", "A"}
        });

        subjectWiseQuestions.put("English", new String[][]{
                {"Which of these is a noun?", "Run", "Quick", "Happiness", "Very", "C"},
                {"Identify the adjective: 'The sky is blue.'", "sky", "is", "blue", "The", "C"},
                {"Which word is a verb?", "Jump", "Red", "Quick", "Happiness", "A"}
        });

        subjectWiseQuestions.put("History", new String[][]{
                {"Who discovered America?", "Columbus", "Newton", "Einstein", "Tesla", "A"},
                {"When did World War II start?", "1914", "1939", "1945", "1965", "B"},
                {"Who was the first President of the USA?", "Lincoln", "Roosevelt", "George Washington", "Adams", "C"}
        });
    }

    private void loadQuestion() {
        radioGroup.clearCheck();
        tvQuestion.setText(questions[currentQuestion][0]);
        rbOptionA.setText(questions[currentQuestion][1]);
        rbOptionB.setText(questions[currentQuestion][2]);
        rbOptionC.setText(questions[currentQuestion][3]);
        rbOptionD.setText(questions[currentQuestion][4]);
    }

    private boolean checkAnswer() {
        int selectedId = radioGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select an answer", Toast.LENGTH_SHORT).show();
            return false;
        }

        String correctAnswer = questions[currentQuestion][5];
        RadioButton selectedRadioButton = findViewById(selectedId);
        String selectedText = selectedRadioButton.getText().toString();

        if ((correctAnswer.equals("A") && selectedText.equals(rbOptionA.getText().toString())) ||
                (correctAnswer.equals("B") && selectedText.equals(rbOptionB.getText().toString())) ||
                (correctAnswer.equals("C") && selectedText.equals(rbOptionC.getText().toString())) ||
                (correctAnswer.equals("D") && selectedText.equals(rbOptionD.getText().toString()))) {
            score++;
        }

        return true;
    }
}
