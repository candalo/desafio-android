package com.picpay.desafio.android

import okhttp3.OkHttpClient
import org.junit.Assert
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class DITest : KoinTest {

    private val retrofit: Retrofit by inject()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startKoin { modules(module) }
        }
    }

    @Test
    fun `Retrofit instance should be created with correct base url`() {
        Assert.assertEquals(BASE_URL, retrofit.baseUrl().toString())
    }

    @Test
    fun `Retrofit instance should be created with correct call factory`() {
        Assert.assertTrue(retrofit.callFactory() is OkHttpClient)
    }

    @Test
    fun `Retrofit instance should be created with gson converter factory`() {
        Assert.assertTrue(retrofit.converterFactories().any { it is GsonConverterFactory })
    }

}