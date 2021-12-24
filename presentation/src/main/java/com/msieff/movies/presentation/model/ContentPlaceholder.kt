package com.msieff.movies.presentation.model

import com.msieff.movies.domain.model.Content
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class ContentPlaceholder(
    override val id: Int = Random().nextInt(),
    override val title: String? = null,
    override val overview: String? = null,
    override val popularity: Double = 0.0,
    override val rating: Double = 0.0,
    override val posterFullPath: String? = null,
    override val backdropFullPath: String? = null

) : Content