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
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Consumer;

public class Questionnaire extends AppCompatActivity {
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

        readData(questionData -> {
            QuestionData question = questionData.get(currentIndex);

            questionText.setText(question.getQuestion());
            option1.setText(question.getRightAnswer());
            option2.setText(question.getWrongAnswer1());
            option3.setText(question.getWrongAnswer2());
            option4.setText(question.getWrongAnswer3());

            option1.setOnClickListener(v -> handleCheckBoxClick(option1));
            option2.setOnClickListener(v -> handleCheckBoxClick(option2));
            option3.setOnClickListener(v -> handleCheckBoxClick(option3));
            option4.setOnClickListener(v -> handleCheckBoxClick(option4));

            next.setOnClickListener(v -> {
                if (selectedOption != null) {
                    if (selectedOption.getText().toString().equals(questionData.get(currentIndex).getRightAnswer())) {
                        Toast.makeText(Questionnaire.this, "+2500 points", Toast.LENGTH_SHORT).show();
                        result = result + 2500;
                        pointsText.setText(String.valueOf(result));
                    } else {
                        Toast.makeText(Questionnaire.this, "-1000 points", Toast.LENGTH_SHORT).show();
                        result = result - 1000;
                        pointsText.setText(String.valueOf(result));
                    }
                }
                if(currentIndex == questionData.size()-1){
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
                    QuestionData questionDataNextButton = questionData.get(currentIndex);
                    questionText.setText(questionDataNextButton.getQuestion());
                    option1.setText(questionDataNextButton.getRightAnswer());
                    option2.setText(questionDataNextButton.getWrongAnswer1());
                    option3.setText(questionDataNextButton.getWrongAnswer2());
                    option4.setText(questionDataNextButton.getWrongAnswer3());

                    option1.setOnClickListener(v1 -> handleCheckBoxClick(option1));
                    option2.setOnClickListener(v1 -> handleCheckBoxClick(option2));
                    option3.setOnClickListener(v1 -> handleCheckBoxClick(option3));
                    option4.setOnClickListener(v1 -> handleCheckBoxClick(option4));

                    option1.setChecked(false);
                    option2.setChecked(false);
                    option3.setChecked(false);
                    option4.setChecked(false);
                }
            });
        });
    }

    private void handleCheckBoxClick(CheckBox checkBox) {
        if (checkBox.isChecked()) {
            if (selectedOption != null && selectedOption != checkBox) {
                selectedOption.setChecked(false);
            }
            selectedOption = checkBox;
        } else {
            selectedOption = null;
        }
    }

    private void readData(Consumer<List<QuestionData>> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("questions").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                List<QuestionData> questionData = new ArrayList<>();
                for(QueryDocumentSnapshot q: task.getResult()){
                    String question = q.getString("question");
                    String rightAnswer = q.getString("rightAnswer");
                    String wrongAnswer1  = q.getString("wrongAnswer1");
                    String wrongAnswer2  = q.getString("wrongAnswer2");
                    String wrongAnswer3  = q.getString("wrongAnswer3");

                    QuestionData qData = new QuestionData(question, wrongAnswer1, wrongAnswer2, wrongAnswer3, rightAnswer);
                    questionData.add(qData);
                }
                callback.accept(questionData);
            }
        });
    }

}