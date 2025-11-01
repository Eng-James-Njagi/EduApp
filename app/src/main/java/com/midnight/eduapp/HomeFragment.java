package com.midnight.eduapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class HomeFragment extends Fragment {

    private EditText searchEditText;
    private ImageView sendButton;
    private ImageView closeButton;
    private TextView decisionTextView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        initializeViews(view);
        Navigation();

        return view;
    }

    private void initializeViews(View view) {
        searchEditText = view.findViewById(R.id.searchEditText);
        sendButton = view.findViewById(R.id.sendButton);
        closeButton = view.findViewById(R.id.closebtn);
        decisionTextView = view.findViewById(R.id.decision);
    }

    private void Navigation() {

        sendButton.setOnClickListener(v -> sendMessage());


        closeButton.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().finishAffinity();
            }
        });


        decisionTextView.setOnClickListener(v -> {
            Intent navDecision = new Intent(getActivity(), LogInActivity.class);
            startActivity(navDecision);
        });
    }

    private void sendMessage() {
        String userMessage = searchEditText.getText().toString().trim();

        if (userMessage.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        navigateToChatFragment(userMessage);
        searchEditText.setText("");
    }

    private void navigateToChatFragment(String initialMessage) {
        ChatFragment chatFragment = new ChatFragment();

        Bundle bundle = new Bundle();
        bundle.putString("initialMessage", initialMessage);
        chatFragment.setArguments(bundle);

        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, chatFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}