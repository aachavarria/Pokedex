package com.example.pokedex.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentLoginBinding
import com.example.pokedex.viewmodels.CurrentUserViewModel
import com.example.pokedex.viewmodels.LoginViewModel
import com.example.pokedex.viewmodels.LoginViewModelType
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginFragment : Fragment(R.layout.fragment_login) {
    private var _binding: FragmentLoginBinding? = null
    private val binding: FragmentLoginBinding get() = _binding!!

    private val disposables = CompositeDisposable()
    lateinit var viewModel: LoginViewModelType
    private lateinit var currentUserViewModel: CurrentUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        currentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposables.clear()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposables.add(
            RxTextView.textChanges(binding.emailAdress)
                .skipInitialValue()
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.email.onNext(it)
                }
        )
        disposables.add(
            RxTextView.textChanges(binding.password)
                .skipInitialValue()
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.password.onNext(it)
                }
        )

        disposables.add(
            viewModel.outputs.isLoginButtonEnabled
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.loginButton.isEnabled = it
                }
        )
        disposables.add(
            RxView.clicks(binding.loginButton)
                .subscribe {
                    viewModel.inputs.loginClicked.onNext(Unit)
                }
        )
        disposables.add(
            viewModel.outputs.emailError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.emailAdress.error = if (it) "Campo requerido" else null
                }
        )

        disposables.add(
            viewModel.outputs.passwordError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    binding.password.error = if (it) "Campo requerido" else null
                }
        )
        disposables.add(
            viewModel.outputs.userFetched
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe {
                    Log.d("test", it.toString())
                    if(it.isEmpty()) {
                        binding.password.error = "Email or password is incorrect"
                        binding.emailAdress.error = "Email or password is incorrect"
                    } else {
                        currentUserViewModel.selectItem(it[0])
                        findNavController().navigate(R.id.action_loginFragmentDest_to_mainFragmentDest)
                    }
                }

        )

        binding.signUpButton.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragmentDest_to_signUpFragmentDest)
        }
    }
}