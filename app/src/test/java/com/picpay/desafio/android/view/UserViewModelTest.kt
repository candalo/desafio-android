package com.picpay.desafio.android.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.picpay.desafio.android.CoroutineRule
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.model.ResponseState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

internal class UserViewModelTest {

    @get:Rule
    internal val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    internal val coroutineRule = CoroutineRule()
    @InjectMocks
    private lateinit var viewModel: UserViewModel
    @Mock
    private lateinit var repository: UserRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Response state with users should be posted to live data`() {
        val user = User(
            "",
            "Lucas Candalo",
            1,
            "candalo"
        )
        repository.stub { onBlocking { getAll() }.doReturn(ResponseState.Success(listOf(user))) }

        val result = viewModel.fetch().blockingObserve()

        assertEquals(user.username, (result as ResponseState.Success).body[0].username)
    }

    private fun <T> LiveData<T>.blockingObserve(): T? {
        var value: T? = null
        val latch = CountDownLatch(1)
        val innerObserver = Observer<T> {
            value = it
            latch.countDown()
        }
        observeForever(innerObserver)
        latch.await(2, TimeUnit.SECONDS)
        return value
    }

}
