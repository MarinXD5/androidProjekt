package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ResultActivity extends AppCompatActivity {

    TextView resultText;
    Button restart, leaderboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        final ExecutorService executorService = Executors.newSingleThreadExecutor();
        final Handler handler = HandlerCompat.createAsync(Looper.getMainLooper());

        resultText = findViewById(R.id.resultText);
        restart = findViewById(R.id.restartButton);
        leaderboard = findViewById(R.id.leaderBoard);

        Intent intent = getIntent();

        executorService.execute(() -> {
            RotateAnimation rotateAnimation = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            rotateAnimation.setDuration(1000);
        });

        handler.post(() -> resultText.setText(String.valueOf(intent.getIntExtra("score", -99999))));

        restart.setOnClickListener(v -> {
            Intent intent1 = new Intent(ResultActivity.this, Questionnaire.class);
            startActivity(intent1);
        });

        leaderboard.setOnClickListener(v -> {
            Intent intent1 = new Intent(ResultActivity.this, LeaderBoard.class);
            startActivity(intent1);
        });
    }

}
