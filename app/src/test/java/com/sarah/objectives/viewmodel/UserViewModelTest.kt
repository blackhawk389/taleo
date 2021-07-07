package com.sarah.objectives.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.UserAPIService
import com.sarah.objectives.datasource.UserRemoteDataSource
import com.sarah.objectives.features.register.viewmodel.UserViewModel
import com.sarah.objectives.repositories.UserRepository
import com.sarah.objectives.utils.LiveDataUtils.getOrAwaitValueTest
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
class UserViewModelTest {

    private lateinit var repository: UserRepository
    private lateinit var dataSource: UserRemoteDataSource

    @Mock
    private lateinit var apiService: UserAPIService

    private lateinit var viewModel: UserViewModel

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)

        dataSource = UserRemoteDataSource(apiService)
        repository = UserRepository(dataSource)
        viewModel = UserViewModel(repository)
    }


    @Test
    fun `register user`() = runBlockingTest {
        val user = TestUtils.user
        viewModel.registerUser(user)
        val liveData = viewModel.registerResponse.getOrAwaitValueTest()
        val response = repository.registerUser(user)
        assertThat(response).isEqualTo(liveData)

    }

    @Test
    fun `enqueue access token request`() = runBlockingTest {
        viewModel.email.value = TestUtils.user.email
        viewModel.password.value = TestUtils.user.password
        viewModel.enqueueTokenRequest()
        val liveData = viewModel.tokenResponse.getOrAwaitValueTest()
        val response = repository.enqueueTokenRequest(viewModel.email.value!!, viewModel.password.value!!)
        assertThat(response).isEqualTo(liveData)
    }

    @Test
    fun `get user details`() = runBlockingTest {
        viewModel.getUserInfo()
        val liveData = viewModel.userInfo.getOrAwaitValueTest()
        val response = repository.getUserInfo()
        assertThat(response).isEqualTo(liveData)
    }
}