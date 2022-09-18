package com.dmsh.galery.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.dmsh.domain.entities.AlbumItem
import com.dmsh.domain.repositories.AlbumRepository
import com.dmsh.domain.usecases.GetAlbumUseCase
import com.dmsh.domain.usecases.GetRemoteAlbumUseCase
import com.dmsh.domain.usecases.SetAlbumUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
@SmallTest
class AlbumViewModelTest {


    @get:Rule
    val instantTaskExecutionRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumViewModel: AlbumViewModel

    private lateinit var getAlbumUseCase: GetAlbumUseCase

    private lateinit var setAlbumUseCase: SetAlbumUseCase

    private lateinit var getRemoteAlbumUseCase: GetRemoteAlbumUseCase

    @Mock
    lateinit var albumRepository: AlbumRepository


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getAlbumUseCase = GetAlbumUseCase(albumRepository)
        albumViewModel = AlbumViewModel(
            getAlbumUseCase, setAlbumUseCase, getRemoteAlbumUseCase
        )
    }

    @Test
    fun getAlbum() {
        runBlocking {
            Mockito.`when`(getAlbumUseCase.invoke()).thenReturn(MutableStateFlow(
                    listOf(AlbumItem(id = 1, albumId = 1, thumbnailUrl = "", title = "" , url = ""))))
            albumViewModel.getDataAlbum()
            val result = albumViewModel.album
            assertEquals(listOf(AlbumItem(id = 1, albumId = 1, thumbnailUrl = "", title = "" , url = "")), result)
        }
    }


}