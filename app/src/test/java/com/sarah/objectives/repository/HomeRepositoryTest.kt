package com.sarah.objectives.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.HomeAPIService
import com.sarah.objectives.datasource.HomeDataSource
import com.sarah.objectives.repositories.HomeRepository
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
class HomeRepositoryTest {
    private lateinit var repository: HomeRepository
    private lateinit var dataSource: HomeDataSource

    @Mock
    private lateinit var apiService: HomeAPIService

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        dataSource = HomeDataSource(apiService)
        repository = HomeRepository(dataSource)

    }

    @Test
    fun `get recent blog`() = runBlockingTest {
        repository.getRecentPosts()
        assertThat(repository.getRecentPosts()).isEqualTo(dataSource.getRecentPosts())
    }

    @Test
    fun `get recent projects`() = runBlockingTest {
        repository.getPhotoItems()
        assertThat(repository.getRecentPosts()).isEqualTo(dataSource.getRecentPhotos())
    }

}