package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.model.ResponseState
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class ResponseCallAdapterFactory : CallAdapter.Factory() {

    override fun get(
        returnType: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        if (Call::class.java != getRawType(returnType)) {
            return null
        }

        check(returnType is ParameterizedType) {
            "return type must be parameterized as Call<ResponseState<<Foo>> or Call<ResponseState<out Foo>>"
        }

        val responseType = getParameterUpperBound(0, returnType)

        if (getRawType(responseType) != ResponseState::class.java) {
            return null
        }

        check(responseType is ParameterizedType) {
            "Response must be parameterized as ResponseState<Foo> or ResponseState<out Foo>"
        }

        val successBodyType = getParameterUpperBound(0, responseType)

        return ResponseCallAdapter<Any>(successBodyType)
    }
}