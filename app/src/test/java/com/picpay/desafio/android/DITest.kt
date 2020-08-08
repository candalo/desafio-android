package com.picpay.desafio.android

import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepository
import junit.framework.TestCase.assertTrue
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.BeforeClass
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class DITest : KoinTest {

    private val retrofit: Retrofit by inject()
    private val service: PicPayService by inject()
    private val repository: UserRepository by inject()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startKoin { modules(listOf(networkModule, dataModule)) }
        }
    }

    @Test
    fun `Retrofit instance should be created with correct base url`() {
        assertEquals(BASE_URL, retrofit.baseUrl().toString())
    }

    @Test
    fun `Retrofit instance should be created with correct call factory`() {
        assertTrue(retrofit.callFactory() is OkHttpClient)
    }

    @Test
    fun `Retrofit instance should be created with gson converter factory`() {
        assertTrue(retrofit.converterFactories().any { it is GsonConverterFactory })
    }

    @Test
    fun `PicPayService instance should be created`() {
        assertNotNull(service.getUsers())
    }

    @Test
    fun `User repository instance should be created`() {
        assertNotNull(repository)
    }

}