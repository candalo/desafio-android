package com.picpay.desafio.android.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.picpay.desafio.android.CoroutineRule
import com.picpay.desafio.android.model.User
import com.picpay.desafio.android.data.UserRepository
import com.picpay.desafio.android.model.ResponseState
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

internal class UserViewModelTest {

    @get:Rule
    internal val instantExecutorRule = InstantTaskExecutorRule()
    @get:Rule
    internal val coroutineRule = CoroutineRule()
    @InjectMocks
    private lateinit var viewModel: UserViewModel
    @Mock
    private lateinit var repository: UserRepository
    @Mock
    private lateinit var observer: Observer<ResponseState<List<User>>>
    @Captor
    private lateinit var captor: ArgumentCaptor<ResponseState<List<User>>>

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

        viewModel.fetch().observeForever(observer)

        verify(observer, times(1)).onChanged(capture(captor))
        assertEquals(user.username, (captor.value as ResponseState.Success).body[0].username)
    }

}
