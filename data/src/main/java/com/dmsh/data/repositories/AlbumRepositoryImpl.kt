package com.dmsh.data.repositories

import com.dmsh.domain.repositories.AlbumRepository
import kotlinx.coroutines.flow.Flow
import com.dmsh.domain.common.Result
import com.dmsh.domain.entities.AlbumItem


class AlbumRepositoryImpl(
    private val localDataSource: AlbumLocalDataSource,
    private val remoteDataSource: AlbumRemoteDataSource
) : AlbumRepository {


    override suspend fun getRemoteAlbum(): Result<List<AlbumItem>?> {
        return remoteDataSource.getRemoteAlbum()
    }

    override suspend fun getAlbum(): Flow<List<AlbumItem>?> {
        return localDataSource.getAlbum()
    }

    override suspend fun setAlbum(albumItem: AlbumItem) {
        localDataSource.saveAlbumItem(albumItem)
    }

}