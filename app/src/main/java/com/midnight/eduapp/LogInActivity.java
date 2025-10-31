package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);

        navigation();
    }
    private void navigation(){
        TextView signUpNav = findViewById(R.id.signUpButton);
        signUpNav.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
        });
        ImageView closebtn = findViewById(R.id.closeBtn);
        closebtn.setOnClickListener(v -> {
            Intent homebtn = new Intent(LogInActivity.this, MainActivity.class);
            startActivity(homebtn);
        });

    }

}
