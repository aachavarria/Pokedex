package com.example.pokedex.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.activities.MainActivity

class LoginFragment : Fragment(R.layout.fragment_login) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val button = view.findViewById<Button>(R.id.loginButton)

        button.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        view.findViewById<Button>(R.id.signUpButton)?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragmentDest_to_signUpFragmentDest)
        }
    }
}