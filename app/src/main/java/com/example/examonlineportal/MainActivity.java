package com.example.examonlineportal;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        // Run seedData in a background thread to avoid UI freezing
        new Thread(() -> {
            dbHelper.seedData();

            // Move to SplashActivity after seeding is done (back on UI thread)
            runOnUiThread(() -> {
                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}
