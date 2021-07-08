package com.sarah.objectives.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

@ExperimentalCoroutinesApi
class PhotoViewModelTest {

    private lateinit var repository: PhotoRepository
    private lateinit var dataSource: PhotoDataSource

    @get:Rule
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()
    private val testDispatcher = TestCoroutineDispatcher()

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
        Dispatchers.setMain(testDispatcher)
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
    fun `get all photos`() = runBlockingTest {
        viewModel.getAllPhotos()
        val liveData = viewModel.allPhotos.getOrAwaitValueTest(5,TimeUnit.SECONDS)
        assertThat(liveData).isEqualTo(repository.getAllPhotos())
    }

}