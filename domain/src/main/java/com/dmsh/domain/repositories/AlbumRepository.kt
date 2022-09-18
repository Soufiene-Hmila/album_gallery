package com.dmsh.domain.repositories

import com.dmsh.domain.common.Result
import com.dmsh.domain.entities.AlbumItem
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    suspend fun getRemoteAlbum(): Result<List<AlbumItem>?>

    suspend fun getAlbum(): Flow<List<AlbumItem>?>

    suspend fun setAlbum(albumItem: AlbumItem)

}