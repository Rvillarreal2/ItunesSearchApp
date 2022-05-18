package com.example.itunessearchapp.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ItunesList(
    val results: List<ItunesItem>
) : Parcelable
