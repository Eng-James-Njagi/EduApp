package com.midnight.eduapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()

        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = mAuth.currentUser

        val skipLogin = intent.getBooleanExtra("SKIP_LOGIN", false)
        if (currentUser != null) {
            reload()
        } else if (!skipLogin) {
            val intent = Intent(this, LogInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun reload() {
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
    }
}