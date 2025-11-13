package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import es.dmoral.toasty.Toasty;

public class LogInActivity extends AppCompatActivity {
    private static final String TAG = "LogInActivity";
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();

        navigation();
        LogInButton();
    }
    private void LogInButton(){
        TextView logbtn = findViewById(R.id.RegisterUser);
        logbtn.setOnClickListener(v -> {
            LogInUser();
        });
    }
    private void navigation(){
        TextView signUpNav = findViewById(R.id.signUpButton);
        signUpNav.setOnClickListener(v -> {
            Intent signUpIntent = new Intent(LogInActivity.this, SignUpActivity.class);
            startActivity(signUpIntent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        });
        ImageView closebtn = findViewById(R.id.closeBtn);
        closebtn.setOnClickListener(v -> {
            Intent homebtn = new Intent(LogInActivity.this, MainActivity.class);
            homebtn.putExtra("SKIP_LOGIN", true);
            startActivity(homebtn);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");

                            Toasty.success(LogInActivity.this, "Login Successful", Toast.LENGTH_SHORT, true).show();

                            emailbtn.setText("");
                            passwordbtn.setText("");

                            Intent intent = new Intent(LogInActivity.this, HomePage.class);
                            intent.putExtra("SKIP_LOGIN", true);
                            startActivity(intent);
                            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                            finish();

                        } else {
                            Log.w(TAG, "signInWithEmail:failure", task.getException());

                            String errorMessage = "Authentication failed";
                            if (task.getException() != null) {
                                errorMessage = task.getException().getMessage();
                            }

                            Toasty.error(LogInActivity.this, errorMessage, Toast.LENGTH_LONG, true).show();
                        }
                    });
        }
    }

}
