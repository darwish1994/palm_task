package com.theminesec.example.question1.feature.chat.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theminesec.example.question1.R
import com.theminesec.example.question1.feature.chat.domain.model.Message

class MessagesAdapter() : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {
    private val data = mutableListOf<Message>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MessageViewHolder = MessageViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.message_view, parent, false)
    )

    override fun onBindViewHolder(
        holder: MessageViewHolder,
        position: Int
    ) {
        holder.bind(data[position])
    }

    fun updateData(newData: List<Message>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val messageText: TextView = view.findViewById(R.id.message_text)
        val messageUser: TextView = view.findViewById(R.id.message_user)

        fun bind(message: Message) {
            messageText.text = message.message
            messageUser.text = message.sender
        }
    }

}