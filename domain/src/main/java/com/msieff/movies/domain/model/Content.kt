package com.msieff.movies.domain.model

import android.os.Parcelable

interface Content: Parcelable {
    val id: Int
    val title: String?
    val overview: String?
    val popularity: Double
    val rating: Double
    val posterFullPath: String?
    val backdropFullPath: String?
}