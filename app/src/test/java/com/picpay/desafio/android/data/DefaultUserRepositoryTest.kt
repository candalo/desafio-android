package com.picpay.desafio.android.data

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.stub
import com.picpay.desafio.android.model.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import retrofit2.Call

class DefaultUserRepositoryTest {

    @InjectMocks
    private lateinit var repository: DefaultUserRepository
    @Mock
    private lateinit var service: PicPayService
    @Mock
    private lateinit var call: Call<List<User>>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `Users count should be returned correctly`() {
        service.stub { onBlocking { getUsers() }.doReturn(getUsers()) }

        val users = runBlocking { repository.getAll() }

        assertEquals(3, users.size)
    }

    private fun getUsers() = listOf(
        User("", "Lucas Candalo", 1, "candalo"),
        User("", "José Souza", 2, "souza"),
        User("", "Henrique Soares", 3, "soares")
    )

}