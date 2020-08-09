package com.picpay.desafio.android

import com.google.gson.GsonBuilder
import com.picpay.desafio.android.data.DefaultUserRepository
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.data.cache.CacheInterceptor
import com.picpay.desafio.android.data.network.ResponseCallAdapterFactory
import com.picpay.desafio.android.view.UserViewModel
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

internal const val BASE_URL = "http://careers.picpay.com/tests/mobdev/"
internal const val CACHE_SIZE = 10 * 1024 * 1024
internal const val CACHE_FILE_PATHNAME = "picpay-cache"

val networkModule = module {
    single {
        Cache(File(androidContext().cacheDir, CACHE_FILE_PATHNAME), CACHE_SIZE.toLong())
    }
    single {
        OkHttpClient.Builder()
            .addNetworkInterceptor(CacheInterceptor())
            .cache(get())
            .build()
    }
    single {
        GsonBuilder().create()
    }
    single {
        ResponseCallAdapterFactory()
    }
    single {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(get())
            .addCallAdapterFactory(get<ResponseCallAdapterFactory>())
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

val viewModule = module {
    viewModel { UserViewModel(get()) }
}