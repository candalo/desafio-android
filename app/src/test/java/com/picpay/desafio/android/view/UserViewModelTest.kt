package com.picpay.desafio.android.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.picpay.desafio.android.User
import com.picpay.desafio.android.data.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*

internal class UserViewModelTest {

    @get:Rule
    internal val instantExecutorRule = InstantTaskExecutorRule()
    @InjectMocks
    private lateinit var viewModel: UserViewModel
    @Mock
    private lateinit var repository: UserRepository
    @Mock
    private lateinit var observer: Observer<List<User>>
    @Captor
    private lateinit var captor: ArgumentCaptor<List<User>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Users should be posted to live data`() {
        val user = User("", "Lucas Candalo", 1, "candalo")
        repository.stub { onBlocking { getAll() }.doReturn(listOf(user)) }
        viewModel.users.observeForever(observer)

        viewModel.fetch().observeForever(observer)

        verify(observer, times(1)).onChanged(capture(captor))
        assertEquals(user.username, captor.value[0].username)
    }

}
