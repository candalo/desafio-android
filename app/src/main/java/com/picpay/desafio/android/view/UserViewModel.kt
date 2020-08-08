package com.picpay.desafio.android.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.picpay.desafio.android.data.UserRepository
import kotlinx.coroutines.Dispatchers

class UserViewModel(private val repository: UserRepository) : ViewModel() {

    fun fetch() = liveData(Dispatchers.IO) {
        emit(repository.getAll())
    }

}