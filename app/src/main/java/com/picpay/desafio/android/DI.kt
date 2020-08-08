package com.picpay.desafio.android

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.DefaultUserRepository
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepository
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal const val BASE_URL = "http://careers.picpay.com/tests/mobdev/"

val networkModule = module {
    single {
        GsonBuilder().create()
    }
    single {
        OkHttpClient.Builder()
            .build()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addConverterFactory(GsonConverterFactory.create(get()))
            .build()
    }
    single {
        get<Retrofit>().create(PicPayService::class.java)
    }
}

val dataModule = module {
    single<UserRepository> { DefaultUserRepository(get()) }
}