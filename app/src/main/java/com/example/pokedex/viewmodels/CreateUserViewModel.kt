package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokedex.db.entities.User
import com.example.pokedex.repositories.PokedexRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

//M V VM
interface CreateUserViewModelInputs {
    val trainerId: Observer<String>
    val password: Observer<String>
    val email: Observer<String>
    val registerClicked: Observer<Unit>
}

//outputs
interface CreateUserViewModelOutputs {
    val isButtonEnabled: Observable<Boolean>
    val userIdError: Observable<Boolean>
    val passwordError: Observable<Boolean>
    val emailError: Observable<Boolean>
    val userCreated: Observable<Boolean>
}

interface CreateUserViewModelType {
    val inputs: CreateUserViewModelInputs
    val outputs: CreateUserViewModelOutputs
}

class CreateUserViewModel(application: Application) : AndroidViewModel(application), CreateUserViewModelInputs, CreateUserViewModelOutputs, CreateUserViewModelType {
    private val repository = PokedexRepository(application.applicationContext)
    //type
    override val inputs: CreateUserViewModelInputs = this
    override val outputs: CreateUserViewModelOutputs = this

    //inputs
    override val trainerId = BehaviorSubject.create<String>()
    override val password = BehaviorSubject.create<String>()
    override val email = BehaviorSubject.create<String>()
    override val registerClicked = PublishSubject.create<Unit>()

    //outputs
    override var userIdError: Observable<Boolean> = Observable.empty()
    override var passwordError: Observable<Boolean> = Observable.empty()
    override var emailError: Observable<Boolean> = Observable.empty()
    override val isButtonEnabled: Observable<Boolean>
    override val userCreated: Observable<Boolean>

    init {
        isButtonEnabled = Observable.combineLatest(trainerId, password, email) { u, p, e ->
            u.isNotEmpty() && p.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(
                e
            ).matches()
        }
        userIdError = trainerId
            .map { it.isEmpty() }
        passwordError = password
            .map { it.isEmpty() }
        emailError = email
            .map { it.isEmpty() }
        userCreated = registerClicked
            .withLatestFrom(trainerId, password, email, {e, n, s, q -> User(trainerId = n, password = q, email = s)})
            .doOnNext{ createUser(it) }
            .map { true }
    }
//    override val isButtonEnabled: Observable<Boolean> =
//        Observable.combineLatest(trainerId, password, email) { u, p, e ->
//            u.isNotEmpty() && p.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(
//                e
//            ).matches()
//        }

       fun createUser(user: User) {
           viewModelScope.launch(Dispatchers.IO) {
               repository.insertUser(user)
           }
       }
}