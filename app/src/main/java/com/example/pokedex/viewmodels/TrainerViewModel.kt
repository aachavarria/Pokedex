package com.example.pokedex.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.Dispatchers
import com.example.pokedex.db.entities.User
import com.example.pokedex.repositories.PokedexRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import androidx.lifecycle.viewModelScope
import io.reactivex.Completable
import io.reactivex.Single
import kotlinx.coroutines.launch


//M V VM
interface TrainerViewModelInputs {
    val trainerId: Observer<String>
    val email: Observer<String>
    val updateClicked: Observer<Unit>
    val logOutClicked: Observer<Unit>
}

//outputs
interface TrainerViewModelOutputs {
    val isUpdateButtonEnabled: Observable<Boolean>
    val trainerIdError: Observable<Boolean>
    val emailError: Observable<Boolean>
    fun updateUser(trainerId: String, email: String, id: Int): Completable
}

interface TrainerViewModelType {
    val inputs: TrainerViewModelInputs
    val outputs: TrainerViewModelOutputs
}

class TrainerViewModel(application: Application) : AndroidViewModel(application),
        TrainerViewModelInputs, TrainerViewModelOutputs, TrainerViewModelType {
    private val repository = PokedexRepository(application.applicationContext)

    //type
    override val inputs: TrainerViewModelInputs = this
    override val outputs: TrainerViewModelOutputs = this

    //inputs
    override val trainerId = BehaviorSubject.create<String>()
    override val email = BehaviorSubject.create<String>()
    override val updateClicked = PublishSubject.create<Unit>()
    override val logOutClicked = PublishSubject.create<Unit>()

    //outputs
    override var trainerIdError: Observable<Boolean> = Observable.empty()
    override var emailError: Observable<Boolean> = Observable.empty()
    override val isUpdateButtonEnabled: Observable<Boolean>

    override fun updateUser(trainerId: String, email: String, id: Int): Completable {
        return repository.updateUser(trainerId, email, id)
    }

    init {

        isUpdateButtonEnabled = Observable.combineLatest(trainerId, email) { p, e ->
            p.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(
                    e
            ).matches()
        }

        trainerIdError = trainerId
                .map { it.isEmpty() }
        emailError = email
                .map { it.isEmpty() }
    }
}