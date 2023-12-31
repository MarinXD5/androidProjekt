package com.example.projekt.Authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt.R;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth auth;

    EditText registerEmail, registerPassword, registerRepeatPassword;
    Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerRepeatPassword = findViewById(R.id.registerRepeatPassword);
        registerButton = findViewById(R.id.register);
        auth = FirebaseAuth.getInstance();

        registerButton.setOnClickListener(v -> {
            if (registerPassword.getText().toString().equals(registerRepeatPassword.getText().toString())) {
                auth.createUserWithEmailAndPassword(registerEmail.getText().toString(), registerPassword.getText().toString()).addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        Toast.makeText(RegisterActivity.this, "User registered successfully!", Toast.LENGTH_SHORT).show();
                        redirectToLogin();
                    }
                });
            }
            else{
                Toast.makeText(this, "There was an error trying to register a user. Please try again!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void redirectToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
