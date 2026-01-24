package com.midnight.eduapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty

class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val TAG = "SignUpActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.signup_activity)

        mAuth = FirebaseAuth.getInstance()

        setupLogInNav()
        setupSignUpButton()
    }

    private fun setupSignUpButton() {
        val signsUp: Button = findViewById(R.id.RegisterUser)
        signsUp.setOnClickListener { registerUser() }
    }

    private fun setupLogInNav() {
        val lognav: TextView = findViewById(R.id.logInButton)
        lognav.setOnClickListener {
            val logNav = Intent(this, LogInActivity::class.java)
            Log.d("LogInActivity", "Moving to LogIn")
            startActivity(logNav)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            finish()
        }

        val closebtn: ImageView = findViewById(R.id.closeBtn)
        closebtn.setOnClickListener {
            val homebtn = Intent(this, MainActivity::class.java)
            startActivity(homebtn)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun registerUser() {
        val email: TextView = findViewById(R.id.emailInput)
        val password: TextView = findViewById(R.id.passwordInput)
        val confirmPassword: TextView = findViewById(R.id.confirmPassword)

        val emailText = email.text.toString()
        val passwordText = password.text.toString().trim()
        val confirmPasswordText = confirmPassword.text.toString().trim()

        if (emailText.isEmpty() || passwordText.isEmpty() || confirmPasswordText.isEmpty()) {
            Toasty.warning(this, "All fields are required", Toast.LENGTH_SHORT, true).show()
        } else if (passwordText != confirmPasswordText) {
            Toasty.warning(this, "Passwords do not match", Toast.LENGTH_SHORT, true).show()
        } else {
            email.text = ""
            password.text = ""
            confirmPassword.text = ""

            mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "createUserWithEmail:success")
                        Toasty.success(this, "Sign Up Successful! Please log in.", Toast.LENGTH_SHORT, true).show()

                        val intent = Intent(this, LogInActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.exception)
                        val errorMessage = task.exception?.message ?: "Sign up failed"
                        Toasty.error(this, errorMessage, Toast.LENGTH_LONG, true).show()
                    }
                }
        }
    }
}