package com.sarah.objectives.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.HomeAPIService
import com.sarah.objectives.datasource.HomeDataSource
import com.sarah.objectives.features.home.viewmodel.HomeViewModel
import com.sarah.objectives.repositories.HomeRepository
import com.sarah.objectives.utils.LiveDataUtils.getOrAwaitValueTest
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
class HomeViewModelTest {

    private lateinit var repository: HomeRepository
    private lateinit var dataSource: HomeDataSource

    @Mock
    private lateinit var apiService: HomeAPIService

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        dataSource = HomeDataSource(apiService)
        repository = HomeRepository(dataSource)
        viewModel = HomeViewModel(repository)

    }

    @Test
    fun `request blogs`() = runBlockingTest {
        viewModel.requestPosts()
        val liveData = viewModel.posts.getOrAwaitValueTest()
        val blog = repository.getRecentPosts()
        assertThat(blog).isEqualTo(liveData)
    }

    @Test
    fun `request projects`() = runBlockingTest {
        viewModel.requestImages()
        val liveData = viewModel.photoItems.getOrAwaitValueTest()
        val projects = repository.getPhotoItems()
        assertThat(projects).isEqualTo(liveData)
    }

}