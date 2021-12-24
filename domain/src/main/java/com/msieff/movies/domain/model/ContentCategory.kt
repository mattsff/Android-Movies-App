package com.msieff.movies.domain.model

data class ContentCategory(
    val category: Category,
    val contents: List<Content>,
)

enum class Category{
    POPULAR, TOP_RATED
}