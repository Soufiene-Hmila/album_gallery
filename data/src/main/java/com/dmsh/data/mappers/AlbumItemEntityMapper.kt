package com.dmsh.data.mappers

import com.dmsh.data.entities.AlbumItemEntity
import com.dmsh.domain.entities.AlbumItem
import kotlinx.coroutines.flow.Flow


class AlbumItemEntityMapper {
    fun toAlbumItemEntity(albumItem: AlbumItem): AlbumItemEntity {
        return AlbumItemEntity(
            id = albumItem.id,
            title = albumItem.title,
            url = albumItem.url,
            thumbnailUrl = albumItem.thumbnailUrl
        )
    }

    fun toAlbumItem(albumItemEntity: AlbumItemEntity): AlbumItem {
        return AlbumItem(1,
            albumItemEntity.id,
            albumItemEntity.thumbnailUrl,
            albumItemEntity.title,
            albumItemEntity.url,
        )
    }
}