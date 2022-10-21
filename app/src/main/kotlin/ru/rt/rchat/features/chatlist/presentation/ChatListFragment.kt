package ru.rt.rchat.features.chatlist.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.rt.rchat.R
import ru.rt.rchat.databinding.FragmentChatsBinding

class ChatListFragment : Fragment(R.layout.fragment_chats) {

    private lateinit var binding: FragmentChatsBinding
    private lateinit var chatListAdapter: ChatListAdapter
//    private val newsViewModel by viewModel<NewsViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentChatsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chatListAdapter = ChatListAdapter()
        binding.chatsRecyclerView.adapter = chatListAdapter
    }
}