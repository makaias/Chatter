package com.chatter.chatter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        registerButton = findViewById(R.id.startActivityRegisterButton);

        registerButton.setOnClickListener((view -> {
            final Intent registrationIntent = new Intent(StartActivity.this, RegisterActivity.class);
            startActivity(registrationIntent);
        }));
    }
}