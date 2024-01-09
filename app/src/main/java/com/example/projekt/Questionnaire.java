package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt.DAO.QuestionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Questionnaire extends AppCompatActivity {

    QuestionData question1 = new QuestionData("Question: What is the primary material used to make the body of a classical guitar?", "Aluminum", "Plastic", "Mahogany", "Fiberglass");
    QuestionData question2 = new QuestionData("Question: Which instrument is known for its reed and brass construction?", "Violin", "Flute", "Harp", "Saxophone ");
    QuestionData question3 = new QuestionData("Question: Which of the following is not a type of drum?", "Snare", "Conga", "Djembe", "Xylophone ");
    QuestionData question4 = new QuestionData("Question: What family does the trumpet belong to in the orchestra?", "String", "Percussion", "Woodwind", "Brass ");
    QuestionData question5 = new QuestionData("Question: Which woodwind instrument uses a double reed?", "Flute", "Saxophone", "Clarinet", "Oboe ");
    QuestionData question6 = new QuestionData("Question: What is the primary material used to make the strings of a violin?", "Steel", "Plastic", "Nylon", "Gut ");
    QuestionData question7 = new QuestionData("Question: Which of the following is a fretted string instrument?", "Harp", "Cello", "Violin", "Banjo ");
    QuestionData question8 = new QuestionData("Question: What is the smallest member of the saxophone family?", "Tenor saxophone", "Baritone saxophone", "Alto saxophone", "Soprano saxophone");
    QuestionData question9 = new QuestionData("Question: Which percussion instrument is played by striking metal bars with mallets?", "Conga", "Snare drum", "Timpani", "Xylophone ");
    QuestionData question10 = new QuestionData("Question: What is the traditional material used to make bagpipes?", "Plastic", "Bamboo", "Metal", "Wood ");

    QuestionData[] questionData = {question1, question2, question3, question4, question5, question6, question7, question8, question9, question10};
    TextView questionText, pointsText, questionNumber;
    CheckBox option1, option2, option3, option4;
    Button next, previous;
    int result = 0;
    int currentIndex = 0;
    FirebaseAuth auth;
    FirebaseFirestore db;
    CheckBox selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionare);

        QuestionData question = questionData[currentIndex];

        next = findViewById(R.id.nextButton);
        previous = findViewById(R.id.previousButton);
        questionText = findViewById(R.id.questionText);
        questionNumber = findViewById(R.id.questionNo);
        pointsText = findViewById(R.id.points);
        option1 = findViewById(R.id.option1);
        option2 = findViewById(R.id.option2);
        option3 = findViewById(R.id.option3);
        option4 = findViewById(R.id.option4);
        pointsText.setText(String.valueOf(result));
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = auth.getCurrentUser();

        questionText.setText(question.getQuestion());
        option1.setText(question.getRightAnswer());
        option2.setText(question.getWrongAnswer1());
        option3.setText(question.getWrongAnswer2());
        option4.setText(question.getWrongAnswer3());

        option1.setOnClickListener(v -> handleCheckBoxClick(option1, questionData[currentIndex]));
        option2.setOnClickListener(v -> handleCheckBoxClick(option2, questionData[currentIndex]));
        option3.setOnClickListener(v -> handleCheckBoxClick(option3, questionData[currentIndex]));
        option4.setOnClickListener(v -> handleCheckBoxClick(option4, questionData[currentIndex]));

        next.setOnClickListener(v -> {

            if(currentIndex == questionData.length-1){
                finish();
                Map<String, Object> data = new HashMap<>();
                data.put("score", result);
                data.put("email", user.getEmail());
                db.collection("leaderboard").add(data).addOnSuccessListener(unused -> Log.d("success: ", "Data successfully added. User: " + user.getEmail() + "score: " + result))
                                                                                       .addOnFailureListener(e -> Log.d("failure: ", "Data failed to be added."));
                Intent intent = new Intent(Questionnaire.this, ResultActivity.class);
                intent.putExtra("score", result);
                startActivity(intent);
            }
            else{
                currentIndex++;
                QuestionData questionDataNextButton = questionData[currentIndex];
                Log.d("DEBUG", String.valueOf(currentIndex));
                Log.d("DEBUG", String.valueOf(questionData.length));
                questionText.setText(questionDataNextButton.getQuestion());
                option1.setText(questionDataNextButton.getRightAnswer());
                option2.setText(questionDataNextButton.getWrongAnswer1());
                option3.setText(questionDataNextButton.getWrongAnswer2());
                option4.setText(questionDataNextButton.getWrongAnswer3());

                option1.setOnClickListener(v1 -> handleCheckBoxClick(option1, questionData[currentIndex]));
                option2.setOnClickListener(v1 -> handleCheckBoxClick(option2, questionData[currentIndex]));
                option3.setOnClickListener(v1 -> handleCheckBoxClick(option3, questionData[currentIndex]));
                option4.setOnClickListener(v1 -> handleCheckBoxClick(option4, questionData[currentIndex]));

                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);
            }
        });
    }

    private void handleCheckBoxClick(CheckBox checkBox, QuestionData question) {
        if (checkBox.isChecked()) {
            if (selectedOption != null && selectedOption != checkBox) {
                selectedOption.setChecked(false);
            }
            if (checkBox.getText().toString().equals(question.getRightAnswer())) {
                Toast.makeText(Questionnaire.this, "+2500 points", Toast.LENGTH_SHORT).show();
                result = result + 2500;
                pointsText.setText(String.valueOf(result));
            } else {
                Toast.makeText(Questionnaire.this, "-1000 points", Toast.LENGTH_SHORT).show();
                result = result - 1000;
                pointsText.setText(String.valueOf(result));
            }
            selectedOption = checkBox;
        } else {
            selectedOption = null;
        }
    }
}