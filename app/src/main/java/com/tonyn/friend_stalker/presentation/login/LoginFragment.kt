package com.tonyn.friend_stalker.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.tonyn.friend_stalker.R
import com.tonyn.friend_stalker.data.remote.AuthApi
import com.tonyn.friend_stalker.data.repository.AuthRepository
import com.tonyn.friend_stalker.domain.usecase.LoginUseCase

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var usernameInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var loginButton: Button
    private lateinit var statusText: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        usernameInput = view.findViewById(R.id.usernameInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        loginButton = view.findViewById(R.id.loginButton)
        statusText = view.findViewById(R.id.statusText)

        val firebaseAuth = FirebaseAuth.getInstance()
        val authApi = AuthApi(firebaseAuth)
        val authRepository = AuthRepository(authApi)
        val loginUseCase = LoginUseCase(authRepository)

        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return LoginViewModel(loginUseCase) as T
            }
        })[LoginViewModel::class.java]

        loginButton.setOnClickListener {
            val username = usernameInput.text.toString()
            val password = passwordInput.text.toString()
            viewModel.login(username, password)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is LoginState.Loading -> statusText.text = "Logging in..."
                is LoginState.Success -> findNavController().navigate(R.id.action_login_to_map)
                is LoginState.Error -> statusText.text = state.message
                is LoginState.Idle -> statusText.text = ""
            }
        }
    }
}