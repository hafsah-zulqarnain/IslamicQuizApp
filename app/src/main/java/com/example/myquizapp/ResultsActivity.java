package com.example.myquizapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;

public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_results);

        TextView resultsTextView = findViewById(R.id.resultsTextView);
        TextView resultsP = findViewById(R.id.resultsPercentage);
        int totalScore = getIntent().getIntExtra("TOTAL_SCORE", 0);
        resultsTextView.setText("Your Score: " + totalScore);
        float percent = ((float) totalScore / 100) * 100;
        resultsP.setText("Total Percentage: " + percent + "%");
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}