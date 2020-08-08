package com.picpay.desafio.android

import android.content.Context
import com.picpay.desafio.android.data.PicPayService
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.data.cache.CacheInterceptor
import com.picpay.desafio.android.view.UserViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.BeforeClass
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.mockito.Mockito.mock
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

internal class DITest : KoinTest {

    private val cache: Cache by inject()
    private val okHttpClient: OkHttpClient by inject()
    private val retrofit: Retrofit by inject()
    private val service: PicPayService by inject()
    private val repository: UserRepository by inject()
    private val viewModel: UserViewModel by inject()

    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            startKoin {
                androidContext(mock(Context::class.java))
                modules(listOf(networkModule, dataModule, viewModule))
            }
        }
    }

    @Test
    fun `Cache instance should be created with correct size`() {
        assertEquals(CACHE_SIZE.toLong(), cache.maxSize())
    }

    @Test
    fun `Cache instance should be created with correct path`() {
        assertEquals(CACHE_FILE_PATHNAME, cache.directory.path)
    }

    @Test
    fun `OkHttpClient instance should be created with correct interceptor`() {
        assertTrue(okHttpClient.networkInterceptors.any { it is CacheInterceptor })
    }

    @Test
    fun `OkHttpClient instance should be created with cache`() {
        assertNotNull(okHttpClient.cache)
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
        assertNotNull(runBlocking { service.getUsers() })
    }

    @Test
    fun `User repository instance should be created`() {
        assertNotNull(repository)
    }

    @Test
    fun `User view model instance should be created`() {
        assertNotNull(viewModel)
    }

}