package com.example.pokedex.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pokedex.db.entities.User

class ItemViewModel : ViewModel() {
    private val mutableSelectedItem = MutableLiveData<User>()
    val selectedItem: LiveData<User> get() = mutableSelectedItem

    fun selectItem(item: User) {
        mutableSelectedItem.value = item
    }
}