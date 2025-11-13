package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class SignUpActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_activity);

        LogInNav();
        SignUpUser();
    }
    private void SignUpUser(){
        Button signsUp = findViewById(R.id.LogInUser);
        signsUp.setOnClickListener(v -> {
            SignInUser();
        });
    }

    private void LogInNav(){
        TextView lognav = findViewById(R.id.logInButton);
        lognav.setOnClickListener(v -> {
            Intent logNav = new Intent(SignUpActivity.this, LogInActivity.class);
            Log.d("Log In Activity", "Moving to LogIn");
            startActivity(logNav);
        });
        ImageView closebtn = findViewById(R.id.closeBtn);
        closebtn.setOnClickListener(v -> {
            Intent homebtn = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(homebtn);
        });

    }
    public void SignInUser(){
       TextView email = findViewById(R.id.emailInput);
       TextView password = findViewById(R.id.passwordInput);
       TextView confirmPassword = findViewById(R.id.confirmPassword);

       String emailText = email.getText().toString();
       String passwordText = password.getText().toString().trim();
       String confirmPasswordText = confirmPassword.getText().toString().trim();


       if(emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()){
           Toasty.warning(this, "All fields are required", Toast.LENGTH_SHORT, true).show();
       }else if(!passwordText.equals(confirmPasswordText)){
           Toasty.warning(this, "Passwords do not match", Toast.LENGTH_SHORT, true).show();

       }else{
           Toasty.success(this, "Sign Up Successful", Toast.LENGTH_SHORT, true).show();
           email.setText("");
           password.setText("");
           confirmPassword.setText("");
       }
    }

}
