package com.dmsh.data.db

import androidx.room.*
import com.dmsh.data.entities.AlbumItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AlbumDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAlbum(album: AlbumItemEntity)

    @Query("SELECT * FROM album")
    fun getAlbum(): Flow<List<AlbumItemEntity>>

    @Delete
    suspend fun deleteAlbum(album: AlbumItemEntity)
}