package com.chatter.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextInputLayout nameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;

    private ProgressDialog registrationProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        nameInputLayout = (TextInputLayout) findViewById(R.id.registerActivityDisplayName);
        emailInputLayout = (TextInputLayout) findViewById(R.id.registerActivityDisplayEmail);
        passwordInputLayout = (TextInputLayout) findViewById(R.id.registerActivityDisplayPassword);
        final Button registerButton = (Button) findViewById(R.id.RegisterActivityCreateAccountButton);
        final Toolbar toolbar = findViewById(R.id.registerApplicationBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        registrationProgressDialog = new ProgressDialog(this);

        registerButton.setOnClickListener(view -> {
            // TODO: check if device is online
            // TODO: check if the user is already logged in
            // the user should not register a new account while logged in
            final String name = nameInputLayout.getEditText().getText().toString();
            final String email = emailInputLayout.getEditText().getText().toString();
            // TODO: password must be stored secure
            final String password = passwordInputLayout.getEditText().getText().toString();
            if(TextUtils.isEmpty(name)){
                Toast.makeText(RegisterActivity.this, "Name required", Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(email)){
                Toast.makeText(RegisterActivity.this, "Email required", Toast.LENGTH_LONG).show();
                return;
            }
            if(TextUtils.isEmpty(password)){
                Toast.makeText(RegisterActivity.this, "Password required", Toast.LENGTH_LONG).show();
                return;
            }
            fireRegistrationProgressDialog();
            registerUser(name, email, password);
        });
    }

    private void fireRegistrationProgressDialog() {
        registrationProgressDialog.setTitle("Registering User");
        registrationProgressDialog.setMessage("Your new account is being registered!");
        registrationProgressDialog.setCanceledOnTouchOutside(false);
        registrationProgressDialog.show();
    }

    private void registerUser(final @NonNull String userName, final @NonNull String email, final @NonNull String password) {
        Preconditions.checkNotEmpty(userName, "userName should not be empty");
        Preconditions.checkNotEmpty(email, "email should not be empty");
        Preconditions.checkNotEmpty(password, "password should not be empty");

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                registrationProgressDialog.dismiss();
                Log.d("RegisterActivity", "createUserWithEmail:success (email: " + email);
                updateUI();
            } else {
                registrationProgressDialog.hide();
                Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                Toast.makeText(RegisterActivity.this, "Authentication failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void updateUI() {
        final Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}