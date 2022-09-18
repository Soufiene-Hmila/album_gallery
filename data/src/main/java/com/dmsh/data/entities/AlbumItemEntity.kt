package com.dmsh.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "album")
data class AlbumItemEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val url: String?,
    val title: String?,
    val thumbnailUrl: String?
) : Serializable