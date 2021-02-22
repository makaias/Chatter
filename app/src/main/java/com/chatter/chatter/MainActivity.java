package com.chatter.chatter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private ViewPager mainViewPager;
    private SectionPagerAdapter sectionPagerAdapter;
    private TabLayout mainTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        final Toolbar mainActivityToolbar = (Toolbar) findViewById(R.id.mainActivityToolbar);
        setSupportActionBar(mainActivityToolbar);
        getSupportActionBar().setTitle("Chatter");

        mainViewPager = findViewById(R.id.mainActivityTabPager);
        sectionPagerAdapter = new SectionPagerAdapter(getSupportFragmentManager());
        mainViewPager.setAdapter(sectionPagerAdapter);

        mainTabLayout = findViewById(R.id.mainActivityTabLayout);
        mainTabLayout.setupWithViewPager(mainViewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        if(currentUser == null){
            updateUIToStartActivity();
        }
    }

    private void updateUIToStartActivity() {
        final Intent startIntent = new Intent(MainActivity.this, StartActivity.class);
        startActivity(startIntent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId() == R.id.mainMenuLogoutItem){
            FirebaseAuth.getInstance().signOut();
            updateUIToStartActivity();
        }
        if(item.getItemId() == R.id.mainMenuAccountSettingsItem){
            final Intent accountSettingsIntent = new Intent(MainActivity.this, AccountSettingsActivity.class);
            startActivity(accountSettingsIntent);
        }
        return true;
    }
}