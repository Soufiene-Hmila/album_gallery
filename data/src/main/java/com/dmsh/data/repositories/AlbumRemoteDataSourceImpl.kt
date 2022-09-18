package com.dmsh.data.repositories


import com.dmsh.data.api.AlbumApi
import com.dmsh.domain.common.*
import com.dmsh.domain.entities.AlbumItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class AlbumRemoteDataSourceImpl(
    private val service: AlbumApi,
) : AlbumRemoteDataSource {
    override suspend fun getRemoteAlbum(): Result<List<AlbumItem>?> =
        withContext(Dispatchers.IO) {
            try {
                val response = service.getAlbum()
                if (response.isSuccessful) {
                    return@withContext Result.Success(response.body())
                } else {
                    return@withContext Result.Error(Exception(response.message()))
                }
            } catch (e: Exception) {
                return@withContext Result.Error(e)
            }
        }
}