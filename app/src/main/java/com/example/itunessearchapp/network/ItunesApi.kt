package com.example.itunessearchapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ItunesApi {

    companion object {
        private const val BASE_URL = "https://itunes.apple.com/"

        fun getItunesApi(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}