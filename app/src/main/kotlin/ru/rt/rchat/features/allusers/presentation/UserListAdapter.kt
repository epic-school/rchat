package ru.rt.rchat.features.allusers.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.rt.rchat.databinding.ListItemChatBinding
import ru.rt.rchat.databinding.ListItemUserBinding
import ru.rt.rchat.features.core.data.User
import ru.rt.rchat.features.core.presentation.ChatWithUserInfo

class UserListAdapter(private val onClickUser: () -> Unit, private val onBlockUserClick: () -> Unit) :
    ListAdapter<User, UserListAdapter.ViewHolder>(ChatDiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position], onClickUser, onBlockUserClick)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemUserBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(private val binding: ListItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: User, onClickUser: () -> Unit, onBlockUserClick: () -> Unit) {
            with(binding) {
                root.setOnClickListener { onClickUser() }
                blockUser.setOnClickListener { onBlockUserClick() }

                userProfileImage.load(item.info.profileImageUrl)
                displayNameText.text = item.info.displayName
            }
        }
    }
}

class ChatDiffCallback : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(oldItem: User, itemWithUserInfo: User): Boolean {
        return oldItem == itemWithUserInfo
    }

    override fun areContentsTheSame(oldItem: User, itemWithUserInfo: User): Boolean {
        return oldItem.info.id == itemWithUserInfo.info.id
    }
}