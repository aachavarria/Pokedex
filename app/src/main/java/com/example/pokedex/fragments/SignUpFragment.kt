package com.example.pokedex.fragments

import com.example.pokedex.R
import com.example.pokedex.databinding.FragmentSignUpBinding
import com.example.pokedex.viewmodels.CreateUserViewModel
import com.example.pokedex.viewmodels.CreateUserViewModelType
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SignUpFragment : Fragment(R.layout.fragment_sign_up) {
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding get() = _binding!!

    private val disposables = CompositeDisposable()
    lateinit var viewModel: CreateUserViewModelType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CreateUserViewModel::class.java)
    }


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
        disposables.clear()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view,savedInstanceState)

        disposables.add(
            RxTextView.textChanges(binding.trainerId)
                .skipInitialValue()
                .map { it.toString() }
                .subscribe {
                    viewModel.inputs.trainerId.onNext(it)
                }
        )
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
            viewModel.outputs.isButtonEnabled
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    binding.registerButton.isEnabled = it
                }
        )
        disposables.add(
            viewModel.outputs.userIdError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    binding.trainerId.error = if (it) "Campo requerido" else null
                }
        )
        disposables.add(
            viewModel.outputs.emailError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    binding.emailAdress.error = if (it) "Campo requerido" else null
                }
        )

        disposables.add(
            viewModel.outputs.passwordError
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    binding.password.error = if (it) "Campo requerido" else null
                }
        )

        disposables.add(
            RxView.clicks(binding.registerButton)
                .subscribe{
                    viewModel.inputs.registerClicked.onNext(Unit)
                }
        )

        disposables.add(
            viewModel.outputs.userCreated
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe{
                    findNavController().navigate(R.id.action_signUpFragmentDest_to_mainFragmentDest)
//                    binding.emailAdress.setText("")
//                    binding.password.setText("")
//                    binding.trainerId.setText("")
                }
        )
    }
}
