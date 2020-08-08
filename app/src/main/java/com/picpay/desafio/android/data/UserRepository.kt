package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.User

interface UserRepository {

    suspend fun getAll(): List<User>

}