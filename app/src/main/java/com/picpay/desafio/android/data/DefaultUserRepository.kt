package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User

class DefaultUserRepository(private val service: PicPayService) : UserRepository {

    override suspend fun getAll(): ResponseState<List<User>> = service.getUsers()

}