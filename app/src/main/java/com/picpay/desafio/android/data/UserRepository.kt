package com.picpay.desafio.android.data

import com.picpay.desafio.android.User

interface UserRepository {

    fun getAll(): List<User>

}