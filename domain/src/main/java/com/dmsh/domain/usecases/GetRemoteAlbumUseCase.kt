package com.dmsh.domain.usecases

import com.dmsh.domain.repositories.AlbumRepository

class GetRemoteAlbumUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.getRemoteAlbum()
}