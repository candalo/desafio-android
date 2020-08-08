package com.picpay.desafio.android.data

import com.picpay.desafio.android.User

class DefaultUserRepository(private val service: PicPayService) : UserRepository {

    override fun getAll(): List<User> = service.getUsers().execute().body() ?: emptyList()

}