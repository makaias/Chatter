package com.chatter.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.internal.Preconditions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountSettingsActivity extends AppCompatActivity {

    private ImageView accountSettingsImageView;
    private TextView accountSettingsNameView;
    private TextView accountSettingsDescriptionView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        accountSettingsImageView = findViewById(R.id.accountSettingsImageView);
        accountSettingsNameView = findViewById(R.id.accountSettingsNameTextView);
        accountSettingsDescriptionView = findViewById(R.id.accountSettingsDescriptionTextView);

        updateAccountSettingsData();
    }

    private void updateAccountSettingsData() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        Preconditions.checkNotNull(currentUser, "currentUser should not be null");
        final String userId = currentUser.getUid();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().
                getReference().
                child("Users").
                child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            // TODO: implement changing profile
            // TODO: implement loading animation until processing the data to hide all default values from the user
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String name = snapshot.child("name").getValue().toString();
                final String image = snapshot.child("image").getValue().toString();
                final String description = snapshot.child("description").getValue().toString();
                final String thumbImage = snapshot.child("thumb_image").getValue().toString();

                accountSettingsNameView.setText(name);
                accountSettingsDescriptionView.setText(description);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}