package com.dmsh.data.repositories

import com.dmsh.data.db.AlbumDao
import com.dmsh.data.mappers.AlbumItemEntityMapper
import com.dmsh.domain.entities.AlbumItem
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class AlbumLocalDataSourceImpl(
    private val albumDao: AlbumDao,
    private val dispatcher: CoroutineDispatcher,
    private val albumItemEntityMapper: AlbumItemEntityMapper,
) : AlbumLocalDataSource {

    override suspend fun saveAlbumItem(albumItem: AlbumItem) = withContext(dispatcher) {
        albumDao.saveAlbum(albumItemEntityMapper.toAlbumItemEntity(albumItem))
    }

    override suspend fun getAlbum(): Flow<List<AlbumItem>?> {
        val savedAlbumItemFlow = albumDao.getAlbum()
        return savedAlbumItemFlow.map { list ->
            list.map { element ->
                albumItemEntityMapper.toAlbumItem(element)
            }
        }
    }
}