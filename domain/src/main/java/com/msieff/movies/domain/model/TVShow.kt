package com.msieff.movies.domain.model

import kotlinx.android.parcel.Parcelize

@Parcelize
data class TVShow(
    override val id: Int,
    override val title: String?,
    override val overview: String?,
    override val popularity: Double,
    override val rating: Double,
    override val posterFullPath: String?,
    override val backdropFullPath: String?
) : Content