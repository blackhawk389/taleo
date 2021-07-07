package com.sarah.objectives.repository

import android.content.Context
import androidx.paging.PagingData
import com.google.common.truth.Truth
import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.config.dao.PostDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.data.projects.Data
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.datasource.PhotoPagedDataSource
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.utils.TestUtils
import com.sarah.objectives.utils.getBlogDao
import com.sarah.objectives.utils.getDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProjectRepositoryTest {

    private lateinit var repository: PhotoRepository
    private lateinit var dataSource: PostDataSource
    private lateinit var pagingDataDataSource: PhotoPagedDataSource

    @Mock
    private lateinit var apiService: PostAPIService

    @Mock
    private lateinit var context: Context
    private lateinit var db: ObjectiveDatabase
    private lateinit var postDao: PostDao


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupDatabase()
        dataSource = PostDataSource(apiService, db)
        pagingDataDataSource = PhotoPagedDataSource(apiService)
        repository = PhotoRepository(dataSource, pagingDataDataSource)

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
    fun `get paginated blog from data source`() = runBlockingTest {
        repository.getPaginatedPhotos()
        val data = TestUtils.project
        val listOfBlog = listOf(data, data, data, data)
        val flow = flow<PagingData<List<Data>>> {}
        flow.collect {
            Truth.assertThat(repository.getPaginatedPhotos()).isEqualTo(listOfBlog)

        }
    }
}