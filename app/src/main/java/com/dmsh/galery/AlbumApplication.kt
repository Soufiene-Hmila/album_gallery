package com.dmsh.galery

import android.app.Application
import com.dmsh.data.repositories.AlbumRepositoryImpl
import com.dmsh.domain.usecases.GetAlbumUseCase
import com.dmsh.domain.usecases.GetRemoteAlbumUseCase
import com.dmsh.domain.usecases.SetAlbumUseCase
import com.dmsh.galery.di.ApplicationModule
import timber.log.Timber


class AlbumApplication : Application() {

    private val albumRepository: AlbumRepositoryImpl
        get() = ApplicationModule.provideAlbumRepository(this)

    val getAlbumUseCase: GetAlbumUseCase
        get() = GetAlbumUseCase(albumRepository)

    val getRemoteAlbumUseCase: GetRemoteAlbumUseCase
        get() = GetRemoteAlbumUseCase(albumRepository)

    val setAlbumUseCase: SetAlbumUseCase
        get() = SetAlbumUseCase(albumRepository)

    override fun onCreate() {
        super.onCreate()
         Timber.plant(Timber.DebugTree())
    }
}