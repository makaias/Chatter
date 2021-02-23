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

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextInputLayout emailInputLayout;
    private TextInputLayout passwordInputLayout;
    private ProgressDialog loginProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        final Button loginActivityLoginButton = findViewById(R.id.loginActivityLoginButton);
        final Toolbar loginActivityToolbar = findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(loginActivityToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Login");

        loginProgressDialog = new ProgressDialog(this);

        emailInputLayout = findViewById(R.id.loginActivityDisplayEmail);
        passwordInputLayout = findViewById(R.id.loginActivityDisplayPassword);

        loginActivityLoginButton.setOnClickListener(view -> {
            // TODO: check if device is online
            // TODO: check if the user is already logged in
            // the user should not register a new account while logged in
            final String email = emailInputLayout.getEditText().getText().toString();
            // TODO: password must be stored secure
            final String password = passwordInputLayout.getEditText().getText().toString();
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(LoginActivity.this, "Email required", Toast.LENGTH_LONG).show();
                return;
            }
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(LoginActivity.this, "Password required", Toast.LENGTH_LONG).show();
                return;
            }
            fireLoginProgressDialog();
            loginUser(email, password);
        });
    }

    private void fireLoginProgressDialog() {
        loginProgressDialog.setTitle("Logging in");
        loginProgressDialog.setMessage("Checking your credentials, please be patient.");
        loginProgressDialog.setCanceledOnTouchOutside(false);
        loginProgressDialog.show();
    }

    private void loginUser(final @NonNull String email, final @NonNull String password) {
        Preconditions.checkNotEmpty(email, "email should not be empty");
        Preconditions.checkNotEmpty(password, "password should not be empty");

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        loginProgressDialog.dismiss();
                        Log.d("LoginActivity", "signInWithEmail:success");
                        updateUI();
                    } else {
                        loginProgressDialog.dismiss();
                        Log.w("LoginActivity", "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_LONG).show();
                        //updateUI(null);
                    }
                });
    }

    private void updateUI() {
        final Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }
}