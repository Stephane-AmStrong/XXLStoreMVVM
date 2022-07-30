package com.example.xxlstoremvvm.ui.auth

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.example.xxlstoremvvm.data.network.Resource
import com.example.xxlstoremvvm.databinding.FragmentLoginBinding
import com.example.xxlstoremvvm.ui.base.BaseFragment
import com.example.xxlstoremvvm.ui.home.HomeActivity
import com.example.xxlstoremvvm.utils.handleApiError
import com.example.xxlstoremvvm.utils.startNewActivity
import com.example.xxlstoremvvm.utils.visible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment: BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private val viewModel : AuthViewModel by viewModels ()
    //private val viewModel by viewModels<AuthenticationViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.progressbar.visible(false)

        viewModel.authenticationResponse.observe(viewLifecycleOwner, Observer {
            binding.progressbar.visible(it is Resource.Loading)
            when (it) {
                is Resource.Success -> {
                    lifecycleScope.launch {
                        viewModel.saveAccessTokens(
                            it.value,
                        )
                        requireActivity().startNewActivity(HomeActivity::class.java)
                    }
                }
                is Resource.Failure -> handleApiError(it) { login() }

                else -> {}
            }
        })


        binding.buttonLogin.setOnClickListener {
            login()
        }
    }

    private fun login() {
        val email = binding.editTextTextEmailAddress.text.toString().trim()
        val password = binding.editTextTextPassword.text.toString().trim()
        viewModel.authentication(email, password)
    }
}