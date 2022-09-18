package com.dmsh.data.api

import com.dmsh.domain.entities.AlbumItem
import retrofit2.Response
import retrofit2.http.GET


interface AlbumApi {
    @GET("img/shared/technical-test.json")
    suspend fun getAlbum(): Response<List<AlbumItem>?>
}