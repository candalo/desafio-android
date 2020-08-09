package com.picpay.desafio.android.model

sealed class ResponseState<out T : Any> {

    data class Success<T : Any>(val body: T) : ResponseState<T>()

    object Error : ResponseState<Nothing>()

}