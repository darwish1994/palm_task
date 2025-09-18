package com.theminesec.example.question1.feature.chat

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.theminesec.example.question1.R
import com.theminesec.example.question1.feature.chat.adapter.MessagesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatFragment : Fragment() {
    val viewModel: ChatViewModel by viewModels()

    lateinit var recyclerView: RecyclerView

    private val adapter by lazy { MessagesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

    /****
    The crash occurs because
    ChatFragment is observing viewModel.messages using the activity lifecycle
    instead of the view lifecycle.
    When the fragment's view is destroyed
     * ****/
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)
        // Legacy code: observing with activity lifecycle, causing crash
        setupObservers()
    }

    fun initViews(view: View) {
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = adapter
    }


    fun setupObservers() {
        viewModel.messages.observe(viewLifecycleOwner) { msgs ->
            Log.v(this.tag, "message count ${msgs.size}")
            adapter.updateData(msgs)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.messages.removeObservers(viewLifecycleOwner)
    }

}

