package com.picpay.desafio.android.view

import com.picpay.desafio.android.data.PicPayService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun networkModule(baseUrl: String) = module {
    single(override = true) {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single(override = true) {
        get<Retrofit>().create(PicPayService::class.java)
    }
}