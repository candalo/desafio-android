package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.User

class DefaultUserRepository(private val service: PicPayService) : UserRepository {

    override suspend fun getAll(): List<User> = service.getUsers()

}