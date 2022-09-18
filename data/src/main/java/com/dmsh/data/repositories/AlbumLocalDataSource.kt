package com.dmsh.data.repositories

import com.dmsh.domain.entities.AlbumItem
import kotlinx.coroutines.flow.Flow

interface AlbumLocalDataSource {
    suspend fun saveAlbumItem(albumItem: AlbumItem)
    suspend fun getAlbum(): Flow<List<AlbumItem>?>
}