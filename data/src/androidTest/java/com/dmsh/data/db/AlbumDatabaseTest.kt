package com.dmsh.data.db

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.dmsh.data.entities.AlbumItemEntity
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.*

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.CountDownLatch

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlbumDatabaseTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumDatabase: AlbumDatabase
    private lateinit var albumDao: AlbumDao

    @Before
    fun setup() {
        albumDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AlbumDatabase::class.java).allowMainThreadQueries().build()

        albumDao = albumDatabase.albumDao()
    }

    @After
    fun teardown() {
        albumDatabase.close()
    }

    @Test
    fun insertShoppingItem() = runBlocking {
        val albumItem = AlbumItemEntity(id = 1, "url", "title", "thumbnailUrl")
        albumDao.saveAlbum(albumItem)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            albumDao.getAlbum().collect {
                assertThat(it).contains(albumItem)
                latch.countDown()
            }
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
    }

    @Test
    fun deleteShoppingItem() = runBlocking {
        val albumItem = AlbumItemEntity(1, "url", "title", "thumbnailUrl")
        albumDao.saveAlbum(albumItem)
        albumDao.deleteAlbum(albumItem)

        val latch = CountDownLatch(1)
        val job = async(Dispatchers.IO) {
            albumDao.getAlbum().collect {
                assertThat(it).doesNotContain(albumItem)
                latch.countDown()
            }
        }
        withContext(Dispatchers.IO) {
            latch.await()
        }
        job.cancelAndJoin()
    }

}