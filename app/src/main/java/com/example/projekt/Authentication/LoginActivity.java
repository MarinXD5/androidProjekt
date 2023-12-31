package com.example.projekt.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt.GamePage;
import com.example.projekt.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    EditText loginEmail, loginPassword;
    Button loginButton;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail = findViewById(R.id.loginEmail);
        loginPassword = findViewById(R.id.loginPassword);
        loginButton = findViewById(R.id.login);
        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(v -> auth.signInWithEmailAndPassword(loginEmail.getText().toString(), loginPassword.getText().toString()).addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                Toast.makeText(this, "User logged in successfully!", Toast.LENGTH_SHORT).show();
                redirectToGame();
            }
        }));
    }

    private void redirectToGame() {
        Intent intent = new Intent(LoginActivity.this, GamePage.class);
        startActivity(intent);
    }
}
