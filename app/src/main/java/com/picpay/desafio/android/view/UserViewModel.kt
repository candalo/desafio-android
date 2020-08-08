package com.picpay.desafio.android.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.picpay.desafio.android.User
import com.picpay.desafio.android.data.UserRepository
import kotlinx.coroutines.Dispatchers

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>>
        get() = _users

    fun fetch() = liveData<List<User>>(Dispatchers.IO) {
        _users.value = repository.getAll()
    }

}