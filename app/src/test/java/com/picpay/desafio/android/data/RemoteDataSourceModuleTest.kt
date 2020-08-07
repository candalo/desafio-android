package com.picpay.desafio.android.data

import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class RemoteDataSourceModuleTest : KoinTest {

    private val retrofit: Retrofit by inject()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startKoin { modules(RemoteDataSourceModule.module) }
        }
    }

    @Test
    fun `Retrofit instance should be created with correct base url`() {
        assertEquals(RemoteDataSourceModule.BASE_URL, retrofit.baseUrl().toString())
    }

    @Test
    fun `Retrofit instance should be created with correct call factory`() {
        assertTrue(retrofit.callFactory() is OkHttpClient)
    }

    @Test
    fun `Retrofit instance should be created with gson converter factory`() {
        assertTrue(retrofit.converterFactories().any { it is GsonConverterFactory })
    }

}