package com.midnight.eduapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import es.dmoral.toasty.Toasty;

public class HomePage extends BaseActivity {

    private EditText searchEditText;
    private ImageView sendButton;
    private ConstraintLayout mainContent;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_changepage);

        initializeViews();
        setupListeners();

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                // Check if fragment container is visible
                if (fragmentContainer.getVisibility() == View.VISIBLE) {
                    // If there are fragments in back stack, pop them
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                        getSupportFragmentManager().popBackStack();

                        // If no more fragments, show main content again
                        if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                            fragmentContainer.setVisibility(View.GONE);
                            mainContent.setVisibility(View.VISIBLE);
                        }
                    }
                } else {
                    // Default back press behavior
                    finish();
                }
            }
        });
    }

    private void initializeViews() {
        searchEditText = findViewById(R.id.searchEditText);
        sendButton = findViewById(R.id.sendButton);
        mainContent = findViewById(R.id.main_content);
        fragmentContainer = findViewById(R.id.fragment_container);
    }

    private void setupListeners() {
        sendButton.setOnClickListener(v -> sendMessage());
    }

    private void sendMessage() {
        String userMessage = searchEditText.getText().toString().trim();

        if (userMessage.isEmpty()) {
            Toasty.warning(this, "Please enter a message to continue", Toast.LENGTH_SHORT).show();
            return;
        }

        navigateToChatFragment(userMessage);
        searchEditText.setText("");
    }

    private void navigateToChatFragment(String initialMessage) {
        // Hide main content and show fragment container
        mainContent.setVisibility(View.GONE);
        fragmentContainer.setVisibility(View.VISIBLE);

        // Create ChatFragment with initial message
        ChatFragment chatFragment = new ChatFragment();

        Bundle bundle = new Bundle();
        bundle.putString("initialMessage", initialMessage);
        chatFragment.setArguments(bundle);

        // Add fragment to container
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, chatFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
