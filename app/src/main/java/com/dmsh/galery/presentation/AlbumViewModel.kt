package com.dmsh.galery.presentation

import androidx.lifecycle.*
import com.dmsh.domain.common.Result
import com.dmsh.domain.entities.AlbumItem
import com.dmsh.domain.usecases.GetAlbumUseCase
import com.dmsh.domain.usecases.GetRemoteAlbumUseCase
import com.dmsh.domain.usecases.SetAlbumUseCase
import kotlinx.coroutines.launch


class AlbumViewModel(
    private val getAlbumUseCase: GetAlbumUseCase,
    private val setAlbumUseCase: SetAlbumUseCase,
    private val getRemoteAlbumUseCase: GetRemoteAlbumUseCase,
) : ViewModel() {

    private val _dataLoading = MutableLiveData(true)
    val dataLoading: LiveData<Boolean> = _dataLoading

    private val _album = MutableLiveData<List<AlbumItem>>()
    val album = _album

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getDataAlbum() {
        viewModelScope.launch {
            _dataLoading.postValue(true)
            when (val albumRemoteData = getRemoteAlbumUseCase.invoke()) {
                is Result.Success -> {

                    albumRemoteData.data?.forEach { albumItem ->
                        setAlbumUseCase.invoke(albumItem)
                    }

                    val albumLocalData = getAlbumUseCase.invoke()

                    albumLocalData.collect { albumResponse ->
                         album.value = albumResponse
                        _dataLoading.postValue(false)
                    }
                }

                is Result.Error -> {
                    _error.postValue(albumRemoteData.exception.message)
                    val albumLocalData = getAlbumUseCase.invoke()
                    albumLocalData.collect { albumResponse ->
                        album.value = albumResponse
                        _dataLoading.postValue(false)
                    }

                }
            }
        }
    }


    @Suppress("UNCHECKED_CAST")
    class BooksViewModelFactory(
        private val getAlbumUseCase: GetAlbumUseCase,
        private val setAlbumUseCase: SetAlbumUseCase,
        private val getRemoteAlbumUseCase: GetRemoteAlbumUseCase,
    ) : ViewModelProvider.NewInstanceFactory() {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AlbumViewModel(
                getAlbumUseCase,
                setAlbumUseCase,
                getRemoteAlbumUseCase,
            ) as T
        }
    }
}