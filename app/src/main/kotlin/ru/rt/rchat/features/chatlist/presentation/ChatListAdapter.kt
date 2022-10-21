package ru.rt.rchat.features.chatlist.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.rt.rchat.databinding.ListItemChatBinding
import ru.rt.rchat.features.core.presentation.ChatWithUserInfo

class ChatListAdapter : ListAdapter<ChatWithUserInfo, ChatListAdapter.ViewHolder>(ChatDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemChatBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(private val binding: ListItemChatBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ChatWithUserInfo) {
            with(binding) {
                userProfileImage.load(item.userInfo.profileImageUrl)
                displayNameText.text = item.userInfo.displayName
                messageText.text = item.chat.lastMessage.text
                timeText.text = item.chat.lastMessage.epochTimeMs.toString()
                onlineView.isVisible = item.userInfo.online
            }
        }
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<ChatWithUserInfo>() {
    override fun areItemsTheSame(oldItem: ChatWithUserInfo, itemWithUserInfo: ChatWithUserInfo): Boolean {
        return oldItem == itemWithUserInfo
    }

    override fun areContentsTheSame(oldItem: ChatWithUserInfo, itemWithUserInfo: ChatWithUserInfo): Boolean {
        return oldItem.chat.info.id == itemWithUserInfo.chat.info.id
    }
}