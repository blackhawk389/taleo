package com.sarah.objectives.viewmodel

import android.content.Context
import androidx.paging.PagingData
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.config.dao.PostDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.data.blogs.PostItems
import com.sarah.objectives.datasource.BlogDataSource
import com.sarah.objectives.datasource.PhotoDataSource
import com.sarah.objectives.features.posts.viewmodel.PostViewModel
import com.sarah.objectives.repositories.PostRepository
import com.sarah.objectives.utils.TestUtils
import com.sarah.objectives.utils.getBlogDao
import com.sarah.objectives.utils.getDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PostViewModelTest {

    private lateinit var repository: PostRepository
    private lateinit var dataSource: BlogDataSource
    private lateinit var pagingDataSource: PhotoDataSource

    @Mock
    private lateinit var apiService: PhotoAPIService

    @Mock
    private lateinit var context: Context
    private lateinit var db: ObjectiveDatabase
    private lateinit var postDao: PostDao
    private lateinit var viewModel: PostViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupDatabase()
        dataSource = BlogDataSource(apiService, db)
        pagingDataSource = PhotoDataSource(apiService)
        repository = PostRepository(dataSource, pagingDataSource)
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
    fun `get paginated blog`() = runBlocking {
        val data = TestUtils.blog
        val listOfBlog = listOf(data, data, data, data)
        val flow = flow<PagingData<List<PostItems>>> {}
        flow.collect {
            assertThat(repository.getPaginatedBlog()).isEqualTo(listOfBlog)

        }
    }
}