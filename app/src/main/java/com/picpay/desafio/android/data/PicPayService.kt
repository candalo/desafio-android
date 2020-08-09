package com.picpay.desafio.android.data

import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import retrofit2.http.GET


interface PicPayService {

    @GET("users")
    suspend fun getUsers(): ResponseState<List<User>>

}