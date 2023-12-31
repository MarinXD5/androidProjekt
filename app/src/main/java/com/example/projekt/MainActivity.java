package com.example.projekt;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.projekt.Authentication.LoginActivity;
import com.example.projekt.Authentication.RegisterActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    Button login, register;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login = findViewById(R.id.loginButton);
        register = findViewById(R.id.registerButton);

        login.setOnClickListener(v -> loginUser());

        register.setOnClickListener(v -> registerUser());

        auth = FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null){
            redirectToGamePage();
        }
    }

    protected void loginUser(){
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    protected void registerUser(){
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

    protected void redirectToGamePage(){
        Intent intent = new Intent(MainActivity.this, GamePage.class);
        startActivity(intent);
    }
}