package com.dmsh.domain.usecases

import com.dmsh.domain.entities.AlbumItem
import com.dmsh.domain.repositories.AlbumRepository

class SetAlbumUseCase(private val albumRepository: AlbumRepository) {
    suspend operator fun invoke(albumItem: AlbumItem) = albumRepository.setAlbum(albumItem)
}