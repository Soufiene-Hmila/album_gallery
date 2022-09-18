package com.dmsh.domain.usecases

import com.dmsh.domain.repositories.AlbumRepository

class GetAlbumUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke() = albumRepository.getAlbum()
}