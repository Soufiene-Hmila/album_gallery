package com.dmsh.domain.entities

data class AlbumItem(
    val albumId: Int,
    val id: Int,
    val thumbnailUrl: String?,
    val title: String?,
    val url: String?
)