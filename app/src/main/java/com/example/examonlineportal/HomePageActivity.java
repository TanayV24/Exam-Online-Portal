package com.example.examonlineportal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomePageActivity extends AppCompatActivity {

    String[] exams = {"Mathematics", "Science", "English", "History"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ListView listView = findViewById(R.id.listViewExams);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, exams);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedExam = exams[position];
            Toast.makeText(HomePageActivity.this, "Starting " + selectedExam, Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomePageActivity.this, ExamInstructionsActivity.class);
            intent.putExtra("exam_name", selectedExam);
            startActivity(intent);
        });
    }
}
