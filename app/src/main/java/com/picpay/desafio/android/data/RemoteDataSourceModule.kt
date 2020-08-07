package com.picpay.desafio.android.data

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RemoteDataSourceModule {

    internal const val BASE_URL = "http://careers.picpay.com/tests/mobdev/"

    val module = module {
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
    }

}