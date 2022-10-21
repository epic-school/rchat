package ru.rt.rchat.features.autorization.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.rt.rchat.R
import ru.rt.rchat.databinding.FragmentLoginBinding
import ru.rt.rchat.features.autorization.presentation.AuthorizationContract.*
import ru.rt.rchat.features.autorization.presentation.AuthorizationViewModel

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel by viewModel<AuthorizationViewModel>()

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginButton.setOnClickListener {
            val email = binding.editTextEmail.text.toString()
            val password = binding.editTextPassword.text.toString()
            viewModel.setEvent(Event.OnLoginClick(email = email, pass = password))
        }
        binding.createUserButton.setOnClickListener { navigateToCreateUser() }
        subscribeState()
        subscribeEffect()
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
            is Effect.NavigateToCreateUser -> navigateToCreateUser()
            is Effect.NavigateToAllUsers -> findNavController().navigate(R.id.action_login_to_users)
        }
    }

    fun navigateToCreateUser() {
        findNavController().navigate(R.id.action_login_to_create)
    }

    private fun handleState(state: State) {
        with(binding) {
            when (state) {
                is State.Initial -> {
                    progressbar.isVisible = false
                    loginButton.isVisible = true
                    errorText.isVisible = false
                }
                is State.Loading -> {
                    progressbar.isVisible = true
                    loginButton.isVisible = false
                    errorText.isVisible = false
                }
                is State.Error -> {
                    progressbar.isVisible = false
                    loginButton.isVisible = true
                    errorText.isVisible = true

                    errorText.text = state.errorModel.message
                }
            }
        }
    }
}