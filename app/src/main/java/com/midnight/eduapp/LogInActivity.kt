package com.midnight.eduapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty

class LogInActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth

    companion object {
        private const val TAG = "LogInActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.login_activity)

        mAuth = FirebaseAuth.getInstance()

        setupNavigation()
        setupLogInButton()
    }

    private fun setupLogInButton() {
        val logbtn: TextView = findViewById(R.id.RegisterUser)
        logbtn.setOnClickListener {
            logInUser()
        }
    }

    private fun setupNavigation() {
        val signUpNav: TextView = findViewById(R.id.signUpButton)
        signUpNav.setOnClickListener {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }

        val closebtn: ImageView = findViewById(R.id.closeBtn)
        closebtn.setOnClickListener {
            val homebtn = Intent(this, MainActivity::class.java)
            homebtn.putExtra("SKIP_LOGIN", true)
            startActivity(homebtn)
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
        }
    }

    private fun logInUser() {
        val emailbtn: TextView = findViewById(R.id.emailInput)
        val passwordbtn: TextView = findViewById(R.id.passwordInput)

        val email = emailbtn.text.toString()
        val password = passwordbtn.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toasty.warning(this, "Please fill all the fields", Toast.LENGTH_SHORT, true).show()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "signInWithEmail:success")
                        Toasty.success(this, "Login Successful", Toast.LENGTH_SHORT, true).show()

                        emailbtn.text = ""
                        passwordbtn.text = ""

                        val intent = Intent(this, HomePage::class.java)
                        intent.putExtra("SKIP_LOGIN", true)
                        startActivity(intent)
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                        finish()
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.exception)
                        val errorMessage = task.exception?.message ?: "Authentication failed"
                        Toasty.error(this, errorMessage, Toast.LENGTH_LONG, true).show()
                    }
                }
        }
    }
}