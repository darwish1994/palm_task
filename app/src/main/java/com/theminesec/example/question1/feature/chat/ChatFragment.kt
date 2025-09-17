package com.theminesec.example.question1.feature.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.theminesec.example.question1.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    private val viewModel: ChatViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        return layoutInflater.inflate(R.layout.fragment_chat, container, false)

    }

    /****
    The crash occurs because
    ChatFragment is observing viewModel.messages using the activity lifecycle
    instead of the view lifecycle.
    When the fragment's view is destroyed
     * ****/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.v(this.tag, "onViewCreated")
        // Legacy code: observing with activity lifecycle, causing crash
        viewModel.messages.observe(viewLifecycleOwner) { msgs ->
//            recyclerView.adapter = MessagesAdapter(msgs)
        }
    }
}

