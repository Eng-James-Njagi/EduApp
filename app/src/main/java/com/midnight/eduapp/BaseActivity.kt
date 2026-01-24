package com.midnight.eduapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import es.dmoral.toasty.Toasty

abstract class BaseActivity : AppCompatActivity() {

    protected lateinit var drawerLayout: DrawerLayout
    private lateinit var mAuth: FirebaseAuth

    override fun setContentView(@LayoutRes layoutResID: Int) {
        super.setContentView(layoutResID)
        mAuth = FirebaseAuth.getInstance()
        setupNavDrawer()
    }

    protected fun setupNavDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)

        if (navigationView == null) {
            return
        }

        val headerView = navigationView.getHeaderView(0)
        val usernameText = headerView.findViewById<TextView>(R.id.username_text)
        usernameText.text = getString(R.string.student_name)

        menuIcon?.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        navigationView.setNavigationItemSelectedListener { item ->
            val id = item.itemId
            handleNavigationItemSelected(id)
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    if (isEnabled) {
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    }
                }
            }
        })
    }

    private fun handleNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.nav_chat -> Toasty.info(this, "Chat clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_research -> Toasty.info(this, "Research clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_flashcards -> Toasty.info(this, "Flashcards clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_quizes -> Toasty.info(this, "Quizes clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_notes -> Toasty.info(this, "Notes clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_summary -> Toasty.info(this, "Summary clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_settings -> Toasty.info(this, "Settings clicked", Toast.LENGTH_SHORT).show()
            R.id.nav_logOut -> {
                Toasty.info(this, "Logout clicked", Toast.LENGTH_SHORT).show()
                mAuth.signOut()
                val intent = Intent(this, LogInActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                finish()
            }
        }
    }
}