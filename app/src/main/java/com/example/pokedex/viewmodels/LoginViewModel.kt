package com.example.pokedex.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.pokedex.db.entities.User
import com.example.pokedex.repositories.PokedexRepository
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

//M V VM
interface LoginViewModelInputs {
    val password: Observer<String>
    val email: Observer<String>
    val loginClicked: Observer<Unit>
}

//outputs
interface LoginViewModelOutputs {
    val isLoginButtonEnabled: Observable<Boolean>
    val passwordError: Observable<Boolean>
    val emailError: Observable<Boolean>
    val userFetched: Observable<List<User>>
}

interface LoginViewModelType {
    val inputs: LoginViewModelInputs
    val outputs: LoginViewModelOutputs
}

class LoginViewModel(application: Application) : AndroidViewModel(application),
    LoginViewModelInputs, LoginViewModelOutputs, LoginViewModelType {
    private val repository = PokedexRepository(application.applicationContext)
    private val mutableSelectedItem = MutableLiveData<User>()

    val selectedItem: LiveData<User> get() = mutableSelectedItem

    //type
    override val inputs: LoginViewModelInputs = this
    override val outputs: LoginViewModelOutputs = this

    //inputs
    override val password = BehaviorSubject.create<String>()
    override val email = BehaviorSubject.create<String>()
    override val loginClicked = PublishSubject.create<Unit>()

    //outputs
    override var passwordError: Observable<Boolean> = Observable.empty()
    override var emailError: Observable<Boolean> = Observable.empty()
    override val isLoginButtonEnabled: Observable<Boolean>
    override val userFetched: Observable<List<User>>

    init {
        isLoginButtonEnabled = Observable.combineLatest(password, email) { p, e ->
            p.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(
                e
            ).matches()
        }

        passwordError = password
            .map { it.isEmpty() }
        emailError = email
            .map { it.isEmpty() }
        userFetched = loginClicked
            .withLatestFrom(
                email,
                password,
                email,
                { e, q, t, s -> User(trainerId = q, password = t, email = s) })
            .switchMap { getUser(it.email, it.password) }
    }


    private fun getUser(email: String, password: String): Observable<List<User>> {
        return repository.getUser(email, password)
    }
}