package com.midnight.eduapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.constraintlayout.widget.ConstraintLayout
import es.dmoral.toasty.Toasty

class HomePage : BaseActivity() {

    private lateinit var searchEditText: EditText
    private lateinit var sendButton: ImageView
    private lateinit var mainContent: ConstraintLayout
    private lateinit var fragmentContainer: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_changepage)

        initializeViews()
        setupListeners()

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (fragmentContainer.visibility == View.VISIBLE) {
                    if (supportFragmentManager.backStackEntryCount > 0) {
                        supportFragmentManager.popBackStack()

                        if (supportFragmentManager.backStackEntryCount == 0) {
                            fragmentContainer.visibility = View.GONE
                            mainContent.visibility = View.VISIBLE
                        }
                    } else {
                        finish()
                    }
                } else {
                    finish()
                }
            }
        })
    }

    private fun initializeViews() {
        searchEditText = findViewById(R.id.searchEditText)
        sendButton = findViewById(R.id.sendButton)
        mainContent = findViewById(R.id.main_content)
        fragmentContainer = findViewById(R.id.fragment_container)
    }

    private fun setupListeners() {
        sendButton.setOnClickListener { sendMessage() }
    }

    private fun sendMessage() {
        val userMessage = searchEditText.text.toString().trim()

        if (userMessage.isEmpty()) {
            Toasty.warning(this, "Please enter a message to continue", Toast.LENGTH_SHORT).show()
            return
        }

        navigateToChatFragment(userMessage)
        searchEditText.setText("")
    }

    private fun navigateToChatFragment(initialMessage: String) {
        mainContent.visibility = View.GONE
        fragmentContainer.visibility = View.VISIBLE

        val chatFragment = ChatFragment().apply {
            arguments = Bundle().apply {
                putString("initialMessage", initialMessage)
            }
        }

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, chatFragment)
            addToBackStack(null)
            commit()
        }
    }
}
