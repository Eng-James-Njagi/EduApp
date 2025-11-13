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

import com.google.firebase.auth.FirebaseAuth;


public class SignUpActivity extends AppCompatActivity {
    private static final String TAG = "SignUpActivity";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.signup_activity);


        mAuth = FirebaseAuth.getInstance();

        LogInNav();
        setUpSignUpButton();
    }
    private void setUpSignUpButton(){
        Button signsUp = findViewById(R.id.RegisterUser);
        signsUp.setOnClickListener(v -> RegisterUser());
    }

    private void LogInNav(){
        TextView lognav = findViewById(R.id.logInButton);
        lognav.setOnClickListener(v -> {
            Intent logNav = new Intent(SignUpActivity.this, LogInActivity.class);
            Log.d("Log In Activity", "Moving to LogIn");
            startActivity(logNav);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        });
        ImageView closebtn = findViewById(R.id.closeBtn);
        closebtn.setOnClickListener(v -> {
            Intent homebtn = new Intent(SignUpActivity.this, MainActivity.class);
            startActivity(homebtn);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

        });

    }
    public void RegisterUser(){
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

           email.setText("");
           password.setText("");
           confirmPassword.setText("");
           
           mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                   .addOnCompleteListener(this, task -> {
                       if (task.isSuccessful()) {
                           Log.d(TAG, "createUserWithEmail:success");
                           Toasty.success(SignUpActivity.this, "Sign Up Successful! Please log in.", Toast.LENGTH_SHORT, true).show();

                           // Navigate to Login Activity
                           Intent intent = new Intent(SignUpActivity.this, LogInActivity.class);
                           startActivity(intent);
                           overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                           finish();

                       } else {
                           Log.w(TAG, "createUserWithEmail:failure", task.getException());

                           String errorMessage = "Sign up failed";
                           if(task.getException() != null){
                               errorMessage = task.getException().getMessage();
                           }
                           Toasty.error(SignUpActivity.this, errorMessage, Toast.LENGTH_LONG, true).show();
                       }
                   });
       }
    }

}
