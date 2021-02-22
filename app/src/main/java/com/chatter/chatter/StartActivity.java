package com.chatter.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button registerButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        registerButton = findViewById(R.id.startActivityRegisterButton);
        loginButton = findViewById(R.id.startActivityLoginButton);

        registerButton.setOnClickListener((view -> {
            final Intent registrationIntent = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(registrationIntent);
        }));

        loginButton.setOnClickListener((view -> {
            final Intent loginIntent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(loginIntent);
        }));
    }
}