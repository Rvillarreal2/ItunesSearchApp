package com.example.itunessearchapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItunesItem(
    val artistId: Int?,
    val artistName: String?,
    val artistViewUrl: String?,
    val artworkUrl100: String?,
    val collectionName: String?,
    val country: String?,
    val description: String?,
    val longDescription: String?,
    val previewUrl: String?,
    val primaryGenreName: String?,
    val releaseDate: String?,
    val shortDescription: String?,
    val trackCount: Int?,
    val trackId: Int?,
    val trackName: String?,
    val trackNumber: Int?,
    val trackPrice: Double?,
) : Parcelable
