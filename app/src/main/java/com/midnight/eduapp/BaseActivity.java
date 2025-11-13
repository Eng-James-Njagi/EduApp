package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import androidx.activity.OnBackPressedCallback;
import com.google.firebase.auth.FirebaseAuth;

import es.dmoral.toasty.Toasty;

public abstract class BaseActivity extends AppCompatActivity {

    protected DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        mAuth = FirebaseAuth.getInstance();
        setupNavDrawer();
    }

    protected void setupNavDrawer() {
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ImageView menuIcon = findViewById(R.id.menu_icon);

        if (drawerLayout == null || navigationView == null) {
            return;
        }

        View headerView = navigationView.getHeaderView(0);
        TextView usernameText = headerView.findViewById(R.id.username_text);
        usernameText.setText(R.string.student_name);

        if (menuIcon != null) {
            menuIcon.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            handleNavigationItemSelected(id);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                } else {
                    if (isEnabled()) {
                        setEnabled(false);
                        getOnBackPressedDispatcher().onBackPressed();
                    }
                }
            }
        });
    }

    private void handleNavigationItemSelected(int itemId) {
        if (itemId == R.id.nav_chat) {
            Toasty.info(this, "Chat clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_research) {
            Toasty.info(this, "Research clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_flashcards) {
            Toasty.info(this, "Flashcards clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_quizes) {
            Toasty.info(this, "Quizes clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_notes) {
            Toasty.info(this, "Notes clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_summary) {
            Toasty.info(this, "Summary clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_settings) {
            Toasty.info(this, "Settings clicked", Toast.LENGTH_SHORT).show();
        } else if (itemId == R.id.nav_logOut) {
            Toasty.info(this, "Logout clicked", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            Intent intent = new Intent(this, LogInActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }
    }
}
