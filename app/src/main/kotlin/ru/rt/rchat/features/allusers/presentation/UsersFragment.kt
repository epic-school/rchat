package ru.rt.rchat.features.allusers.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.rt.rchat.R
import ru.rt.rchat.databinding.FragmentUsersBinding
import ru.rt.rchat.features.allusers.presentation.UsersContract.*

class UsersFragment : Fragment(R.layout.fragment_users) {

    private val viewModel by viewModel<UserViewModel>()

    private lateinit var binding: FragmentUsersBinding
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListAdapter = UserListAdapter(
            onClickUser = { viewModel.setEvent(Event.OnUserClick) },
            onBlockUserClick = { viewModel.setEvent(Event.OnBanUserClick) }
        )
        binding.usersRecyclerView.adapter = userListAdapter
        subscribeState()
        subscribeEffect()
        viewModel.setEvent(Event.OnViewReady)
    }

    private fun subscribeState() {
        lifecycleScope.launch {
            viewModel.uiState.collect { handleState(it) }
        }
    }

    private fun subscribeEffect() {
        lifecycleScope.launch {
            viewModel.effect.collect { handleEffect(it) }
        }
    }

    private fun handleEffect(effect: Effect) {
        when (effect) {
            is Effect.DialogMessage -> {}
            is Effect.DialogError -> {}
        }
    }

    private fun handleState(state: State) {
        when (state) {
            is State.Loading -> {
                binding.progressbar.isVisible = true
                binding.usersRecyclerView.isVisible = false
                binding.errorText.isVisible = false
            }
            is State.Error -> {
                binding.progressbar.isVisible = false
                binding.usersRecyclerView.isVisible = false
                binding.errorText.isVisible = true

                binding.errorText.text = state.errorModel.message
            }
            is State.Content -> {
                binding.progressbar.isVisible = false
                binding.usersRecyclerView.isVisible = true
                binding.errorText.isVisible = false

                (binding.usersRecyclerView.adapter as UserListAdapter).submitList(state.users)
            }
        }
    }
}