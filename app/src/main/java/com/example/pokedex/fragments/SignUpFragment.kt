package com.example.pokedex.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentSignUpBinding
import com.example.pokedex.viewmodels.CreateUserViewModel

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    val viewModel: CreateUserViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)
        val userId = binding.userId
        val password = binding.password
        val email = binding.emailAddress

        binding.registerButton.setOnClickListener {
//            findNavController().navigate(R.id.loginFragmentDest)
            viewModel.createUser(userId.toString(), password.toString(), email.toString())
        }
    }
}
