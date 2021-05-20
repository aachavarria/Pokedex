package com.example.pokedex.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.pokedex.R
import com.example.pokedex.activities.LoginActivity
import com.example.pokedex.adapter.PokemonCardAdapter
import com.example.pokedex.databinding.FragmentTrainerBinding
import com.example.pokedex.db.entities.User
import com.example.pokedex.utils.Utils.hideKeyboard
import com.example.pokedex.viewmodels.*
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class TrainerFragment : Fragment(R.layout.fragment_trainer) {

    private var _binding: FragmentTrainerBinding? = null
    private val binding: FragmentTrainerBinding get() = _binding!!

    private lateinit var currentUserViewModel: CurrentUserViewModel
    private lateinit var currentUser : User

    private val disposables = CompositeDisposable()
    lateinit var viewModel: TrainerViewModelType

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrainerViewModel::class.java)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTrainerBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        disposables.clear()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentUserViewModel = ViewModelProvider(requireActivity()).get(CurrentUserViewModel::class.java)

        currentUserViewModel.selectedItem.observe(viewLifecycleOwner, { user ->
            currentUser = user
            binding.trainerId.setText(currentUser.trainerId)
            binding.emailAdress.setText(currentUser.email)
            disposables.add(
                    RxView.clicks(binding.updateButton)
                            .subscribe {
                                viewModel.outputs.updateUser(binding.trainerId.text.toString(), binding.emailAdress.text.toString(), currentUser.id.toInt())
                                        .subscribeOn(Schedulers.io())
                                        .subscribe {
                                            val intent = Intent (activity, LoginActivity::class.java)
                                            activity?.startActivity(intent)
                                        }
                            }
            )
        })

        disposables.add(
                RxTextView.textChanges(binding.emailAdress)
                        .skipInitialValue()
                        .map { it.toString() }
                        .subscribe {
                            viewModel.inputs.email.onNext(it)
                        }
        )
        disposables.add(
                RxTextView.textChanges(binding.trainerId)
                        .skipInitialValue()
                        .map { it.toString() }
                        .subscribe {
                            viewModel.inputs.trainerId.onNext(it)
                        }
        )
        disposables.add(
                viewModel.outputs.trainerIdError
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
                viewModel.outputs.isUpdateButtonEnabled
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe{
                            binding.updateButton.isEnabled = it
                        }
        )
        disposables.add(
            RxView.clicks(binding.logOutButton)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    val intent = Intent (activity, LoginActivity::class.java)
                    activity?.startActivity(intent)
                }
        )
    }
}