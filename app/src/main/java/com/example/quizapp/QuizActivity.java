package com.example.quizapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

/** @noinspection ALL*/
public class QuizActivity extends AppCompatActivity {

    private LinearLayout questionContainer;
    private Button btnSubmit;
    private TextView tvScore;

    // Questions and answers
    private final String[] questions = {
            "What is the capital of France?",
            "Who wrote 'Hamlet'?",
            "Which planet is known as the Red Planet?",
            "What is the boiling point of water?",
            "Who painted the Mona Lisa?",
            "Which is the largest ocean on Earth?",
            "Who discovered gravity?",
            "What is the chemical symbol for gold?",
            "Which country hosts the Great Barrier Reef?",
            "What is the square root of 144?"
    };

    private final String[][] options = {
            {"Paris", "London", "Rome", "Berlin"},
            {"Shakespeare", "Hemingway", "Tolstoy", "Orwell"},
            {"Mars", "Jupiter", "Saturn", "Venus"},
            {"100°C", "90°C", "120°C", "80°C"},
            {"Leonardo da Vinci", "Van Gogh", "Picasso", "Rembrandt"},
            {"Pacific", "Atlantic", "Indian", "Arctic"},
            {"Newton", "Einstein", "Galileo", "Tesla"},
            {"Au", "Ag", "Fe", "Cu"},
            {"Australia", "USA", "India", "Brazil"},
            {"12", "10", "14", "16"}
    };

    private final int[] correctAnswers = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionContainer = findViewById(R.id.question_container);
        btnSubmit = findViewById(R.id.btn_submit);
        tvScore = findViewById(R.id.tv_score);

        // Dynamically create question views
        for (int i = 0; i < questions.length; i++) {
            addQuestionView(i);
        }

        btnSubmit.setOnClickListener(v -> calculateScore());
    }

    @SuppressLint("SetTextI18n")
    private void addQuestionView(int questionIndex) {
        TextView tvQuestion = new TextView(this);
        tvQuestion.setText((questionIndex + 1) + ". " + questions[questionIndex]);
        tvQuestion.setTextSize(16);
        tvQuestion.setTextColor(getResources().getColor(R.color.primary_color)); // Question text color
        tvQuestion.setPadding(0, 16, 0, 8);

        RadioGroup rgOptions = new RadioGroup(this);
        rgOptions.setOrientation(RadioGroup.VERTICAL);
        rgOptions.setTag(questionIndex); // To identify question index later

        for (int i = 0; i < options[questionIndex].length; i++) {
            RadioButton rbOption = new RadioButton(this);
            rbOption.setText(options[questionIndex][i]);
            rbOption.setTextSize(14);
            rbOption.setTextColor(getResources().getColor(R.color.option_text_color)); // Set option text color here
            rgOptions.addView(rbOption);
        }

        questionContainer.addView(tvQuestion);
        questionContainer.addView(rgOptions);
    }


    @SuppressLint("SetTextI18n")
    private void calculateScore() {
        int score = 0;

        for (int i = 0; i < questionContainer.getChildCount(); i++) {
            View view = questionContainer.getChildAt(i);
            if (view instanceof RadioGroup) {
                RadioGroup rgOptions = (RadioGroup) view;
                int questionIndex = (int) rgOptions.getTag();
                int selectedId = rgOptions.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    int selectedIndex = rgOptions.indexOfChild(findViewById(selectedId));
                    if (selectedIndex == correctAnswers[questionIndex]) {
                        score++;
                    }
                }
            }
        }

        tvScore.setText("Your Score: " + score + "/" + questions.length);
        tvScore.setVisibility(View.VISIBLE);
        btnSubmit.setEnabled(false); // Disable submit after score is shown
        Toast.makeText(this, "Quiz Completed!", Toast.LENGTH_SHORT).show();
    }
}
