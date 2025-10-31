package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.core.splashscreen.SplashScreen;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SplashScreen.installSplashScreen(this);
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.visitor_chat_page);

        Navigation();
    }
    private void Navigation(){
        TextView nav = findViewById(R.id.decision);
        nav.setOnClickListener(v -> {
            Intent navDecision = new Intent(MainActivity.this, LogInActivity.class);
            startActivity(navDecision);
        });
    }
}