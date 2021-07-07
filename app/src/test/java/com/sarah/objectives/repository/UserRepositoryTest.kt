package com.sarah.objectives.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.UserAPIService
import com.sarah.objectives.datasource.UserRemoteDataSource
import com.sarah.objectives.repositories.UserRepository
import com.sarah.objectives.utils.TestUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private lateinit var dataSource: UserRemoteDataSource

    @Mock
    private lateinit var apiService: UserAPIService

    val user = TestUtils.user

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        dataSource = UserRemoteDataSource(apiService)
        repository = UserRepository(dataSource)
    }

    @Test
    fun `register user`() = runBlockingTest {
        repository.registerUser(user)
        assertThat(repository.registerUser(user)).isEqualTo(dataSource.registerUser(user))
    }

    @Test
    fun  `enqueue token request`() = runBlockingTest {
        val user = TestUtils.user
        repository.enqueueTokenRequest(user.email!!,user.password!!)
        assertThat(repository.enqueueTokenRequest(user.email!!,user.password!!)).isEqualTo(dataSource.enqueueTokenRequest(TestUtils.tokenRequestBody))
    }

    @Test
    fun `get user information`() = runBlockingTest {
        repository.getUserInfo()
        assertThat(repository.getUserInfo()).isEqualTo(dataSource.getUserInfo())
    }


}