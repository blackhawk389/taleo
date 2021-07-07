package com.sarah.objectives.repository

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.PhotoAPIService
import com.sarah.objectives.config.dao.PostDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.datasource.PhotoDataSource
import com.sarah.objectives.repositories.PhotoRepository
import com.sarah.objectives.utils.getBlogDao
import com.sarah.objectives.utils.getDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PhotoRepositoryTest {

    private lateinit var repository: PhotoRepository
    private lateinit var dataSource: PhotoDataSource

    @Mock
    private lateinit var apiService: PhotoAPIService

    @Mock
    private lateinit var context: Context
    private lateinit var db: ObjectiveDatabase
    private lateinit var postDao: PostDao


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        setupDatabase()
        dataSource = PhotoDataSource(apiService, db)
        repository = PhotoRepository(dataSource)

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
    fun `get all photos` () = runBlockingTest {
        repository.getAllPhotos()
        assertThat(repository.getAllPhotos()).isEqualTo(dataSource.getAllPhotos())
    }
}