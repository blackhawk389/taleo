package com.sarah.objectives.viewmodel

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.config.dao.PhotoDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.datasource.PhotoDataSource
import com.sarah.objectives.features.photos.viewmodel.PhotoViewModel
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.utils.LiveDataUtils.getOrAwaitValueTest
import com.sarah.objectives.utils.getDatabase
import com.sarah.objectives.utils.getProjectDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PhotoViewModelTest {

    private lateinit var repository: PhotoRepository
    private lateinit var dataSource: PhotoDataSource

    @Mock
    private lateinit var apiService: PhotoAPIService

    @Mock
    private lateinit var context: Context
    private lateinit var db: ObjectiveDatabase
    private lateinit var photoDao: PhotoDao
    private lateinit var viewModel: PhotoViewModel


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupDatabase()
        dataSource = PhotoDataSource(apiService, db)
        repository = PhotoRepository(dataSource)
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
    fun `get all photos`() = runBlocking {
        viewModel.getAllPhotos()
        val liveData = viewModel.allPhotos.getOrAwaitValueTest()
        assertThat(viewModel.getAllPhotos()).isEqualTo(liveData)
    }

}