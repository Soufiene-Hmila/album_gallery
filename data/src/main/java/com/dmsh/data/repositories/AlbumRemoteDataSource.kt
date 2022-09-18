package com.dmsh.data.repositories

import com.dmsh.domain.common.*
import com.dmsh.domain.entities.AlbumItem

interface AlbumRemoteDataSource {
    suspend fun getRemoteAlbum(): Result<List<AlbumItem>?>
}