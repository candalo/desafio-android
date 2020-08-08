package com.picpay.desafio.android.view

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.capture
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.picpay.desafio.android.User
import com.picpay.desafio.android.data.UserRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.*
import org.mockito.Mockito.`when`

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
        `when`(repository.getAll()).thenReturn(listOf(user))
        viewModel.users.observeForever(observer)

        viewModel.fetch()

        verify(observer, times(1)).onChanged(capture(captor))
        assertEquals(user.username, captor.value[0].username)
    }

}
