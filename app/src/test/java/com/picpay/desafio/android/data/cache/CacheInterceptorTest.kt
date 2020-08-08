package com.picpay.desafio.android.data.cache

import okhttp3.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations

class CacheInterceptorTest {

    @InjectMocks
    private lateinit var interceptor: CacheInterceptor
    @Mock
    private lateinit var chain: Interceptor.Chain
    private lateinit var response: Response

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        response = spy(
            Response.Builder()
                .request(Request.Builder().url("http://xpto.com").build())
                .protocol(Protocol.HTTP_2)
                .code(200)
                .message("")
                .build()
        )
    }

    @Test
    fun `Request should be intercepted and cache configuration should be added`() {
        `when`(chain.proceed(chain.request())).thenReturn(response)

        val response = interceptor.intercept(chain)

        assertNotNull(response.header("Cache-Control"))
        assertTrue(response.header("Cache-Control")?.contains("max-age=3600") ?: false)
    }

}