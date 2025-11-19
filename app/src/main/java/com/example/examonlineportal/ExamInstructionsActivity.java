package com.example.examonlineportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ExamInstructionsActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_instructions);

        // Make sure activity_exam_instructions.xml has these views with correct IDs
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvExamName = findViewById(R.id.tvExamName);
        Button btnStartExam = findViewById(R.id.btnStartExam);

        // Get the exam name passed from the previous screen
        String examName = getIntent().getStringExtra("exam_name");
        if (examName != null && !examName.isEmpty()) {
            tvExamName.setText(examName + " Exam");
        } else {
            tvExamName.setText("Exam Instructions");
        }

        // On button click, go to the Exam Page
        btnStartExam.setOnClickListener(v -> {
            Intent intent = new Intent(ExamInstructionsActivity.this, ExamPageActivity.class);
            intent.putExtra("exam_name", examName);
            startActivity(intent);
        });
    }
}
