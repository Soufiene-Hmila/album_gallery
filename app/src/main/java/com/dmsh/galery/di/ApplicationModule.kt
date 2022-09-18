package com.dmsh.galery.di

import android.content.Context
import com.dmsh.data.api.NetworkModule
import com.dmsh.data.repositories.AlbumLocalDataSource
import com.dmsh.data.repositories.AlbumLocalDataSourceImpl
import com.dmsh.data.repositories.AlbumRemoteDataSourceImpl
import com.dmsh.data.repositories.AlbumRepositoryImpl
import com.dmsh.data.db.AlbumDatabase
import com.dmsh.data.mappers.AlbumItemEntityMapper
import kotlinx.coroutines.Dispatchers


object ApplicationModule {


    private const val baseUrl = "https://static.leboncoin.fr/"

    private var database: AlbumDatabase? = null

    private val networkModule by lazy {
        NetworkModule()
    }

    private val albumItemEntityMapper by lazy {
        AlbumItemEntityMapper()
    }

    @Volatile
    var albumRepository: AlbumRepositoryImpl? = null

    fun provideAlbumRepository(context: Context): AlbumRepositoryImpl {
        synchronized(this) {
            return albumRepository ?: createAlbumRepository(context)
        }
    }

    private fun createAlbumRepository(context: Context): AlbumRepositoryImpl {
        val newRepo = AlbumRepositoryImpl(createBooksLocalDataSource(context),
                AlbumRemoteDataSourceImpl(networkModule.createAlbumApi(baseUrl)))
        albumRepository = newRepo
        return newRepo
    }

    private fun createBooksLocalDataSource(context: Context): AlbumLocalDataSource {
        val database = database ?: createDataBase(context)
        return AlbumLocalDataSourceImpl(
            database.albumDao(),
            Dispatchers.IO,
            albumItemEntityMapper
        )
    }

    private fun createDataBase(context: Context): AlbumDatabase {
        val result = AlbumDatabase.getDatabase(context)
        database = result
        return result
    }
}