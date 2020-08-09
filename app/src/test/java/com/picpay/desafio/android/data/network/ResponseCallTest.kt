package com.picpay.desafio.android.data.network

import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.model.ResponseState
import com.picpay.desafio.android.model.User
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

internal class ResponseCallTest {

    @InjectMocks
    private lateinit var responseCall: ResponseCall<List<User>>
    @Mock
    private lateinit var call: Call<List<User>>
    @Mock
    private lateinit var callback: Callback<ResponseState<List<User>>>
    @Mock
    private lateinit var response: Response<List<User>>
    @Captor
    private lateinit var captor: ArgumentCaptor<Response<ResponseState<List<User>>>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Successful response behavior`() {
        val user = User("", "Lucas Candalo", 1, "candalo")

        `when`(call.enqueue(any())).thenAnswer { invocation ->
            `when`(response.isSuccessful).thenReturn(true)
            `when`(response.body()).thenReturn(listOf(user))
            val arg = invocation.arguments[0] as Callback<List<User>>
            arg.onResponse(mock<Call<List<User>>>(), response)
        }

        responseCall.enqueue(callback)

        verify(callback, times(1)).onResponse(any(), capture(captor))
        assertEquals(
            user.username,
            (captor.value.body() as ResponseState.Success<List<User>>).body[0].username
        )
    }

    @Test
    fun `Error response behavior`() {
        `when`(call.enqueue(any())).thenAnswer { invocation ->
            `when`(response.isSuccessful).thenReturn(false)
            val arg = invocation.arguments[0] as Callback<List<User>>
            arg.onResponse(mock<Call<List<User>>>(), response)
        }

        responseCall.enqueue(callback)

        verify(callback, times(1)).onResponse(any(), capture(captor))
        assertTrue(captor.value.body() is ResponseState.Error)
    }

    @Test(expected = UnsupportedOperationException::class)
    fun `Execute should throw an exception`() {
        responseCall.execute()
    }

}