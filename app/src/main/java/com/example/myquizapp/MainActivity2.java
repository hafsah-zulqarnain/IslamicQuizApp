package com.example.myquizapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.res.Resources;
import android.util.Log;
import android.content.Intent;
import android.widget.Button;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {
    private int currentQuestionIndex = 0;
    private String[] questions;
    private String[][] options;
    private String[] answers;

    private int total;
    private TextView questionTextView;
    private RadioGroup optionsGroup;
    private Button prevButton;
    private Button nextButton;
    private Button showAnswerbutton;
    private TextView totalTextView;
    private Button endexambutton;
    private TextView timerTextView;

    private Handler handler = new Handler(Looper.getMainLooper());
    private Runnable timerRunnable;
    private int remainingTime = 180;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        questions = new String[20];
        options = new String[20][4];
        answers = new String[20];
        total=0;
        loadQuestions();

        questionTextView = findViewById(R.id.question);
        optionsGroup = findViewById(R.id.options_group);
        //prevButton = findViewById(R.id.prev);
        nextButton = findViewById(R.id.next);
        showAnswerbutton = findViewById(R.id.showAnswer);
        totalTextView = findViewById(R.id.total_score);
        timerTextView = findViewById(R.id.timer_text_view);
        endexambutton = findViewById(R.id.endExam);
        Log.d("MainActivity2", answers[currentQuestionIndex]);

        setQuestion();

        startTimer();
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (currentQuestionIndex < questions.length - 1) {

                    int selectedId = optionsGroup.getCheckedRadioButtonId();
                    if (selectedId != -1) {
                        //Log.d("MainActivity2", String.valueOf(selectedId));
                        RadioButton selectedRadioButton = findViewById(selectedId);
                        String selectedOption = selectedRadioButton.getText().toString();
                        //Log.d("MainActivity2", selectedOption);
                        //Log.d("MainActivity2", answers[currentQuestionIndex]);
                        if (answers[currentQuestionIndex].equals(selectedOption)) {
                            total += 5;
                            Log.d("MainActivity2", "Matched");
                        }
                        else{
                            total = total -1;
                        }
                        totalTextView.setText(String.valueOf(total));
                    }

                    else {
                    //totalTextView.setText("No option Selected");
                     //   Log.d("MainActivity2", "No option selected");
                        total = total -1;
                    }
                    currentQuestionIndex++;
                    setQuestion();
                    showAnswerbutton.setText("Show Answer");
                }
            }
        });

        /*prevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (currentQuestionIndex > 0) {
                showAnswerbutton.setText("Show Answer");
                currentQuestionIndex--;
                setQuestion();
                }
            }
        });*/

        showAnswerbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentQuestionIndex > -1) {
                    showAnswerbutton.setText(answers[currentQuestionIndex]);
                    total = total -1;
                    totalTextView.setText(String.valueOf(total));
                }
            }
        });

        endexambutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = optionsGroup.getCheckedRadioButtonId();
                if (selectedId != -1) {
                    //Log.d("MainActivity2", String.valueOf(selectedId));
                    RadioButton selectedRadioButton = findViewById(selectedId);
                    String selectedOption = selectedRadioButton.getText().toString();
                    //Log.d("MainActivity2", selectedOption);
                    //Log.d("MainActivity2", answers[currentQuestionIndex]);
                    if (answers[currentQuestionIndex].equals(selectedOption)) {
                        total += 5;
                        Log.d("MainActivity2", "Matched");
                    }
                    else{
                        total = total -1;
                    }
                    totalTextView.setText(String.valueOf(total));
                }
                Intent intent = new Intent(MainActivity2.this, ResultsActivity.class);
                intent.putExtra("TOTAL_SCORE", total);
                startActivity(intent);
                finish();
            }
        });
    }
    private void loadQuestions() {
        Resources res = getResources();
        for (int i = 1; i <= 20; i++) {
            String questionKey = "question_" + i;
            String option1Key = "option_" + i + "_1";
            String option2Key = "option_" + i + "_2";
            String option3Key = "option_" + i + "_3";
            String option4Key = "option_" + i + "_4";
            String answerKey = "answer_" + i;

            questions[i - 1] = res.getString(res.getIdentifier(questionKey, "string", getPackageName()));
            options[i - 1][0] = res.getString(res.getIdentifier(option1Key, "string", getPackageName()));
            options[i - 1][1] = res.getString(res.getIdentifier(option2Key, "string", getPackageName()));
            options[i - 1][2] = res.getString(res.getIdentifier(option3Key, "string", getPackageName()));
            options[i - 1][3] = res.getString(res.getIdentifier(option4Key, "string", getPackageName()));
            answers[i - 1] = res.getString(res.getIdentifier(answerKey, "string", getPackageName()));
        }
    }

    private void setQuestion() {
        questionTextView.setText(questions[currentQuestionIndex]);
        RadioButton option1Button = findViewById(R.id.option_1);
        RadioButton option2Button = findViewById(R.id.option_2);
        RadioButton option3Button = findViewById(R.id.option_3);
        RadioButton option4Button = findViewById(R.id.option_4);

        option1Button.setText(options[currentQuestionIndex][0]);
        option2Button.setText(options[currentQuestionIndex][1]);
        option3Button.setText(options[currentQuestionIndex][2]);
        option4Button.setText(options[currentQuestionIndex][3]);

        //prevButton.setEnabled(currentQuestionIndex > 0);
        nextButton.setEnabled(currentQuestionIndex < questions.length - 1);
        if (currentQuestionIndex == questions.length - 1) {
            endexambutton.setVisibility(View.VISIBLE);
            nextButton.setVisibility(View.GONE);
        } else {
            endexambutton.setVisibility(View.GONE);
            nextButton.setVisibility(View.VISIBLE);
        }
    }
    private void startTimer() {
        timerRunnable = new Runnable() {
            @Override
            public void run() {
                if (remainingTime > 0) {
                    remainingTime--;
                    int minutes = remainingTime / 60;
                    int seconds = remainingTime % 60;
                    timerTextView.setText(String.format("Time left: %02d:%02d", minutes, seconds));
                    handler.postDelayed(this, 1000);
                }
                else {
                    Intent intent = new Intent(MainActivity2.this, ResultsActivity.class);
                    intent.putExtra("TOTAL_SCORE", total);
                    startActivity(intent);
                    finish();
                }
            }
        };
        handler.post(timerRunnable);
    }
}