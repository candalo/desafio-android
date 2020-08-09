package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.model.ResponseState
import retrofit2.Call
import retrofit2.CallAdapter
import java.lang.reflect.Type

class ResponseCallAdapter<T : Any>(
    private val successType: Type
) : CallAdapter<T, Call<ResponseState<T>>> {

    override fun adapt(call: Call<T>): Call<ResponseState<T>> = ResponseCall(call)

    override fun responseType(): Type = successType

}