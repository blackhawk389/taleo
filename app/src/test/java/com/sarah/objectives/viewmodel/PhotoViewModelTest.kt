package com.sarah.objectives.viewmodel

import android.content.Context
import androidx.paging.PagingData
import com.google.common.truth.Truth
import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.config.dao.PhotoDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.data.projects.Data
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.datasource.PhotoPagedDataSource
import com.sarah.objectives.features.photos.viewmodel.PhotoViewModel
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.utils.TestUtils
import com.sarah.objectives.utils.getDatabase
import com.sarah.objectives.utils.getProjectDao
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
class PhotoViewModelTest {

    private lateinit var repository: PhotoRepository
    private lateinit var dataSource: PostDataSource
    private lateinit var pagingDataDataSource: PhotoPagedDataSource

    @Mock
    private lateinit var apiService: PostAPIService

    @Mock
    private lateinit var context: Context
    private lateinit var db: ObjectiveDatabase
    private lateinit var photoDao: PhotoDao
    private lateinit var viewModel: PhotoViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupDatabase()
        dataSource = PostDataSource(apiService, db)
        pagingDataDataSource = PhotoPagedDataSource(apiService)
        repository = PhotoRepository(dataSource, pagingDataDataSource)
        viewModel = PhotoViewModel(repository)

    }

    private fun setupDatabase() {
        db = getDatabase(context)
        photoDao = getProjectDao(db)
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun `get paginated blog`() = runBlocking {
        val data = TestUtils.project
        val listOfProjects = listOf(data, data, data, data)
        val flow = flow<PagingData<List<Data>>> {}
        flow.collect {
            Truth.assertThat(repository.getPaginatedPhotos()).isEqualTo(listOfProjects)

        }
    }
}