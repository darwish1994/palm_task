package com.theminesec.example.question1.feature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.theminesec.example.question1.R
import com.theminesec.example.question1.feature.chat.ChatFragment


class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        view.findViewById<Button>(R.id.btn_chat).setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ChatFragment())
                .addToBackStack("ddd")
                .commit()

        }

        return view
    }


}