package com.sarah.objectives.repository

import android.content.Context
import com.google.common.truth.Truth.assertThat
import com.sarah.objectives.apiservice.PostAPIService
import com.sarah.objectives.config.dao.PostDao
import com.sarah.objectives.config.db.ObjectiveDatabase
import com.sarah.objectives.datasource.PostDataSource
import com.sarah.objectives.repositories.PostRepository
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
class PostRepositoryTest {
    private lateinit var repository: PostRepository
    private lateinit var dataSource: PostDataSource

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
        repository = PostRepository(dataSource)

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
    fun `get all posts` () = runBlockingTest {
        repository.getAllPosts()
        assertThat(repository.getAllPosts()).isEqualTo(dataSource.getAllPosts())
    }
}
