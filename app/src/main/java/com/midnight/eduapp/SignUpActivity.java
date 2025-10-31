package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_activity);

        LogInNav();
    }
    private void LogInNav(){
        TextView lognav = findViewById(R.id.logInButton);
        lognav.setOnClickListener(v -> {
            Intent logNav = new Intent(SignUpActivity.this, LogInActivity.class);
            Log.d("Log In Activity", "Moving to LogIn");
            startActivity(logNav);
        });
    }
}
