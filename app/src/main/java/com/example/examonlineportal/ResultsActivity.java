package com.example.examonlineportal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ResultsActivity extends AppCompatActivity {

    TextView txtScore, txtResult;
    Button btnBackToHome;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        txtScore = findViewById(R.id.txtScore);
        txtResult = findViewById(R.id.txtResult);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        if (txtScore != null && txtResult != null && btnBackToHome != null) {
            int score = getIntent().getIntExtra("score", 0);
            int total = getIntent().getIntExtra("total", 0);

            txtScore.setText("Your Score:");
            txtResult.setText(score + " / " + total);

            // Show performance message as a Toast or skip if not needed

            btnBackToHome.setOnClickListener(v -> {
                Intent intent = new Intent(ResultsActivity.this, HomePageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        } else {
            throw new RuntimeException("UI elements are missing in activity_results.xml!");
        }
    }
}
