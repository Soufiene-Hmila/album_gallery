package com.dmsh.data.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule {


    private val loggingInterceptor by lazy {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        loggingInterceptor
    }

    private val httpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor).build()
    }

    private fun getRetrofit(baseURL: String): Retrofit {
        return Retrofit.Builder().baseUrl(baseURL)
            .client(httpClient).addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().create())).build()
    }

    fun createAlbumApi(baseURL: String): AlbumApi {
        val retrofit = getRetrofit(baseURL)
        return retrofit.create(AlbumApi::class.java)
    }
}