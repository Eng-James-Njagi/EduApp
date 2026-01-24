package com.midnight.eduapp

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import es.dmoral.toasty.Toasty

class HomeFragment : Fragment() {

    private lateinit var searchEditText: EditText
    private lateinit var sendButton: ImageView
    private lateinit var closeButton: ImageView
    private lateinit var decisionTextView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)

        initializeViews(view)
        setupNavigation()

        return view
    }

    private fun initializeViews(view: View) {
        searchEditText = view.findViewById(R.id.searchEditText)
        sendButton = view.findViewById(R.id.sendButton)
        closeButton = view.findViewById(R.id.closebtn)
        decisionTextView = view.findViewById(R.id.decision)
    }

    private fun setupNavigation() {
        sendButton.setOnClickListener { sendMessage() }

        closeButton.setOnClickListener {
            activity?.finishAffinity()
        }

        decisionTextView.setOnClickListener {
            activity?.let {
                val navDecision = Intent(it, LogInActivity::class.java)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    it.overrideActivityTransition(
                        Activity.OVERRIDE_TRANSITION_OPEN,
                        android.R.anim.fade_in,
                        android.R.anim.fade_out
                    )
                }
                startActivity(navDecision)
                it.finish()
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                    it.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }
    }

    private fun sendMessage() {
        val userMessage = searchEditText.text.toString().trim()

        if (userMessage.isEmpty()) {
            Toasty.warning(requireContext(), "Please enter a message to continue", Toast.LENGTH_SHORT).show()
            return
        }

        navigateToChatFragment(userMessage)
        searchEditText.setText("")
    }

    private fun navigateToChatFragment(initialMessage: String) {
        val chatFragment = ChatFragment().apply {
            arguments = Bundle().apply {
                putString("initialMessage", initialMessage)
            }
        }

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, chatFragment)
            addToBackStack(null)
            commit()
        }
    }
}