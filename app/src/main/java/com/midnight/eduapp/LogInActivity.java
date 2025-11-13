package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import es.dmoral.toasty.Toasty;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);

        navigation();
        LogInButton();
    }
    private void LogInButton(){
        TextView logbtn = findViewById(R.id.LogInUser);
        logbtn.setOnClickListener(v -> {
            LogInUser();
        });
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
    public void LogInUser(){
        TextView emailbtn = findViewById(R.id.emailInput);
        TextView passwordbtn = findViewById(R.id.passwordInput);

        String email = emailbtn.getText().toString();
        String password = passwordbtn.getText().toString().trim();

        if(email.isEmpty() || password.isEmpty()){
            Toasty.warning(this, "Please fill all the fields", Toast.LENGTH_SHORT, true).show();
        }
        else{
            Toasty.success(this, "Login Successful", Toast.LENGTH_SHORT, true).show();
            emailbtn.setText("");
            passwordbtn.setText("");
        }
    }

}
