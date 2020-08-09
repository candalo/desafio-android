package com.picpay.desafio.android.data.network

import com.picpay.desafio.android.model.ResponseState
import okhttp3.Request
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseCall<T : Any>(private val call: Call<T>) : Call<ResponseState<T>> {

    override fun enqueue(callback: Callback<ResponseState<T>>) {
        call.enqueue(object : Callback<T> {
            override fun onFailure(call: Call<T>, t: Throwable) {
                callback.onResponse(
                    this@ResponseCall,
                    Response.success(ResponseState.Error)
                )
            }

            override fun onResponse(call: Call<T>, response: Response<T>) {
                val body = response.body()

                if (response.isSuccessful && body != null) {
                    callback.onResponse(
                        this@ResponseCall,
                        Response.success(ResponseState.Success(body))
                    )
                } else {
                    callback.onResponse(
                        this@ResponseCall,
                        Response.success(ResponseState.Error)
                    )
                }

            }
        })
    }

    override fun isExecuted(): Boolean = call.isExecuted

    override fun clone(): Call<ResponseState<T>> = ResponseCall(call.clone())

    override fun isCanceled(): Boolean = call.isCanceled

    override fun cancel() = call.cancel()

    override fun execute(): Response<ResponseState<T>> =
        throw UnsupportedOperationException("ResponseCall doesn't support execute")

    override fun request(): Request = call.request()

}