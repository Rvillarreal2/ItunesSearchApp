package com.example.itunessearchapp.network

import com.example.itunessearchapp.data.ItunesList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ItunesApiService {

    @GET("search?")
    fun searchMusic(
        @Query("term") term: String,
        @Query("media") media: String
    ): Call<ItunesList>
}