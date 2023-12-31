package com.example.projekt;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt.Authentication.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class GamePage extends AppCompatActivity {

    TextView userEmail;
    FirebaseAuth auth;
    Button startGame, logout, celebration;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        userEmail = findViewById(R.id.userMail);
        startGame = findViewById(R.id.startGameButton);
        logout = findViewById(R.id.logoutButton);
        celebration = findViewById(R.id.celebration);

        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();

        assert user != null;
        userEmail.setText("Hello: " + user.getEmail() + " :)");

        startGame.setOnClickListener(v -> {
            Intent intent = new Intent(GamePage.this, Questionnaire.class);
            startActivity(intent);
        });

        logout.setOnClickListener(v -> {
            auth.signOut();
            redirectToLogin();
        });

        celebration.setOnClickListener(v ->{
            Intent intent = new Intent(GamePage.this, ResultActivity.class);
            startActivity(intent);
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(GamePage.this, LoginActivity.class);
        startActivity(intent);
    }
}
