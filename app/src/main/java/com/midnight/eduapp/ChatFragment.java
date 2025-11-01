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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatFragment extends Fragment {

    private RecyclerView chatRecyclerView;
    private EditText inputMessage;
    private ImageView sendBtn;
    private ImageView closeBtn;
    private TextView decisionTextView;

    private ChatAdapter chatAdapter;
    private List<ChatMessage> chatMessages;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.chat_fragment, container, false);

        initializeViews(view);
        setupRecyclerView();
        Navigation();
        handleInitialMessage();

        return view;
    }

    private void initializeViews(View view) {
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView);
        inputMessage = view.findViewById(R.id.inputMessage);
        sendBtn = view.findViewById(R.id.sendBtn);
        closeBtn = view.findViewById(R.id.closebtn);
        decisionTextView = view.findViewById(R.id.decision);

        chatMessages = new ArrayList<>();
    }

    private void setupRecyclerView() {
        chatAdapter = new ChatAdapter(chatMessages, getContext());

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setStackFromEnd(true);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(chatAdapter);
    }

    private void Navigation() {

        sendBtn.setOnClickListener(v -> sendMessage());


        closeBtn.setOnClickListener(v -> {
            if (getActivity() != null) {
                requireActivity().getOnBackPressedDispatcher().onBackPressed();
            }
        });
        decisionTextView.setOnClickListener(v -> {
            Intent navDecision = new Intent(getActivity(), LogInActivity.class);
            startActivity(navDecision);
        });
    }

    private void handleInitialMessage() {
        if (getArguments() != null) {
            String initialMessage = getArguments().getString("initialMessage");
            if (initialMessage != null && !initialMessage.isEmpty()) {
                addMessage(initialMessage, true);
                simulateBotResponse(initialMessage);
            }
        }
    }

    private void sendMessage() {
        String message = inputMessage.getText().toString().trim();

        if (message.isEmpty()) {
            Toast.makeText(getContext(), "Please enter a message", Toast.LENGTH_SHORT).show();
            return;
        }

        addMessage(message, true);
        inputMessage.setText("");
        simulateBotResponse(message);
    }

    private void addMessage(String message, boolean isSentByUser) {
        ChatMessage chatMessage = new ChatMessage(message, isSentByUser);
        chatMessages.add(chatMessage);
        chatAdapter.notifyItemInserted(chatMessages.size() - 1);
        chatRecyclerView.scrollToPosition(chatMessages.size() - 1);
    }

    private void simulateBotResponse(String userMessage) {
        chatRecyclerView.postDelayed(() -> {
            String botResponse = "I received your message: \"" + userMessage + "\". How can I help you further?";
            addMessage(botResponse, false);
        }, 1000);
    }
}
