package com.chatter.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AccountEditActivity extends AppCompatActivity {

    private Toolbar accountEditToolbar;
    private TextInputLayout accountEditDescriptionLayout;
    private Button saveChangesButton;
    private DatabaseReference databaseReference;
    private FirebaseUser currentUser;
    private ProgressDialog accountEditProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_edit);
        setupCustomToolbar();
        setupFirebaseDatabaseReference();
        setupEditables();
        setupSaveChangesButton();
    }

    private void setupCustomToolbar() {
        accountEditToolbar = findViewById(R.id.accountEditActivityBarLayout);
        setSupportActionBar(accountEditToolbar);
        getSupportActionBar().setTitle("Edit Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void setupFirebaseDatabaseReference() {
        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Preconditions.checkNotNull(currentUser, "currentUser should not be null");
        final String userId = currentUser.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
    }

    private void setupEditables() {
        final String descriptionFromAccountSettings = getIntent().getStringExtra("description");
        accountEditDescriptionLayout = findViewById(R.id.accountEditDescriptionLayout);
        accountEditDescriptionLayout.getEditText().setText(descriptionFromAccountSettings);
    }

    private void setupSaveChangesButton() {
        saveChangesButton = findViewById(R.id.accountEditSaveChangesButton);
        saveChangesButton.setOnClickListener(view -> {
            fireAccountEditProgressDialog();
            final String description = accountEditDescriptionLayout.getEditText().getText().toString();
            databaseReference.child("description").setValue(description).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    accountEditProgressDialog.dismiss();
                    final Intent accountSettingsIntent = new Intent(AccountEditActivity.this, AccountSettingsActivity.class);
                    startActivity(accountSettingsIntent);
                } else{
                    accountEditProgressDialog.dismiss();
                    Log.w("AccountEditActivity", "save changes:failure", task.getException());
                    Toast.makeText(AccountEditActivity.this, "Failed to save changes", Toast.LENGTH_LONG).show();
                }
            });
        });
    }

    private void fireAccountEditProgressDialog() {
        accountEditProgressDialog = new ProgressDialog(AccountEditActivity.this);
        accountEditProgressDialog.setTitle("Saving changes");
        accountEditProgressDialog.setMessage("Your changes are being saved, please be patient!");
        accountEditProgressDialog.setCanceledOnTouchOutside(false);
        accountEditProgressDialog.show();
    }
}