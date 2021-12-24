package com.msieff.movies.presentation.model

enum class ContentType(val id: Int) {
    MOVIES(0),
    TV_SHOWS(1)
}

fun getContentTypeById(id: Int): ContentType? {
    return ContentType.values().find { it.id == id }
}