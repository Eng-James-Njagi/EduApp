package com.midnight.eduapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(
    private val chatMessages: List<ChatMessage>,
) : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    companion object {
        private const val VIEW_TYPE_USER = 1
        private const val VIEW_TYPE_BOT = 2
    }

    override fun getItemViewType(position: Int): Int {
        val message = chatMessages[position]
        return if (message.isSentByUser) {
            VIEW_TYPE_USER
        } else {
            VIEW_TYPE_BOT
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = if (viewType == VIEW_TYPE_USER) {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_user, parent, false)
        } else {
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_chat_ai, parent, false)
        }
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val message = chatMessages[position]
        holder.messageTextView.text = message.message
    }

    override fun getItemCount(): Int {
        return chatMessages.size
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val messageTextView: TextView = itemView.findViewById(R.id.messageText)
    }
}