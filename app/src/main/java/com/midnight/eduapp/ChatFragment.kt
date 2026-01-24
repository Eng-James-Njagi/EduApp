package com.midnight.eduapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChatFragment : Fragment() {

    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var inputMessage: EditText
    private lateinit var sendBtn: ImageView
    private lateinit var closeBtn: ImageView
    private lateinit var decisionTextView: TextView

    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatMessages: MutableList<ChatMessage>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.chat_fragment, container, false)

        initializeViews(view)
        setupRecyclerView()
        setupNavigation()
        handleInitialMessage()

        return view
    }

    private fun initializeViews(view: View) {
        chatRecyclerView = view.findViewById(R.id.chatRecyclerView)
        inputMessage = view.findViewById(R.id.inputMessage)
        sendBtn = view.findViewById(R.id.sendBtn)
        closeBtn = view.findViewById(R.id.closebtn)
        decisionTextView = view.findViewById(R.id.decision)

        chatMessages = ArrayList()
    }

    private fun setupRecyclerView() {
        chatAdapter = ChatAdapter(chatMessages)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.stackFromEnd = true
        chatRecyclerView.layoutManager = layoutManager
        chatRecyclerView.adapter = chatAdapter
    }

    private fun setupNavigation() {
        sendBtn.setOnClickListener { sendMessage() }

        closeBtn.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }
        decisionTextView.setOnClickListener {
            val navDecision = Intent(activity, LogInActivity::class.java)
            startActivity(navDecision)
        }
    }

    private fun handleInitialMessage() {
        arguments?.getString("initialMessage")?.let { initialMessage ->
            if (initialMessage.isNotEmpty()) {
                addMessage(initialMessage, true)
                simulateBotResponse(initialMessage)
            }
        }
    }

    private fun sendMessage() {
        val message = inputMessage.text.toString().trim()

        if (message.isEmpty()) {
            Toast.makeText(context, "Please enter a message", Toast.LENGTH_SHORT).show()
            return
        }

        addMessage(message, true)
        inputMessage.setText("")
        simulateBotResponse(message)
    }

    private fun addMessage(message: String, isSentByUser: Boolean) {
        val chatMessage = ChatMessage(message, isSentByUser)
        chatMessages.add(chatMessage)
        chatAdapter.notifyItemInserted(chatMessages.size - 1)
        chatRecyclerView.scrollToPosition(chatMessages.size - 1)
    }

    private fun simulateBotResponse(userMessage: String) {
        chatRecyclerView.postDelayed({
            val botResponse = "I received your message: \"$userMessage\". How can I help you further?"
            addMessage(botResponse, false)
        }, 1000)
    }
}