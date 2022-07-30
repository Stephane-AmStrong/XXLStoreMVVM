package com.example.xxlstoremvvm.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import com.example.xxlstoremvvm.R
import com.example.xxlstoremvvm.data.models.AccountDto
import com.example.xxlstoremvvm.data.network.Resource
import com.example.xxlstoremvvm.databinding.FragmentHomeBinding
import com.example.xxlstoremvvm.utils.handleApiError
import com.example.xxlstoremvvm.utils.logout
import com.example.xxlstoremvvm.utils.visible

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel by viewModels<HomeViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.progressbar.visible(false)

        viewModel.getUser()

        viewModel.user.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Resource.Success -> {
                    binding.progressbar.visible(false)
                    updateUI(it.value)
                }
                is Resource.Loading -> {
                    binding.progressbar.visible(true)
                }
                is Resource.Failure -> {
                    handleApiError(it)
                }
            }
        })

        binding.buttonLogout.setOnClickListener {
            logout()
        }
    }

    private fun updateUI(user: AccountDto.UserInfoResponse) {
        with(binding) {
            textViewName.text = user.name
            textViewEmail.text = user.email
        }
    }
}