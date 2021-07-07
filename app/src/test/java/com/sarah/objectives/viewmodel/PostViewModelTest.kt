package com.sarah.objectives.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.config.dao.PostDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.features.posts.viewmodel.PostViewModel
import com.sarah.objectives.repositories.PostRepository
import com.sarah.objectives.utils.LiveDataUtils.getOrAwaitValueTest
import com.sarah.objectives.utils.getBlogDao
import com.sarah.objectives.utils.getDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PostViewModelTest {

    private lateinit var repository: PostRepository
    private lateinit var dataSource: PostDataSource

    @Mock
    private lateinit var apiService: PostAPIService

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val dispatcher = TestCoroutineDispatcher()
    @Mock
    private lateinit var context: Context
    private lateinit var db: ObjectiveDatabase
    private lateinit var postDao: PostDao
    private lateinit var viewModel: PostViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(dispatcher)
        setupDatabase()
        dataSource = PostDataSource(apiService, db)
        repository = PostRepository(dataSource)
        viewModel = PostViewModel(repository)

    }

    private fun setupDatabase() {
        db = getDatabase(context)
        postDao = getBlogDao(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun  `get all posts` () = runBlockingTest {
        viewModel.getAllPosts()
        assertThat(viewModel.getAllPosts()).isEqualTo(repository.getAllPosts())
    }

}