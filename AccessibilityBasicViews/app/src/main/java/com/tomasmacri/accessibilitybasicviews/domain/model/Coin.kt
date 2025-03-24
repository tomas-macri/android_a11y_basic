package com.tomasmacri.accessibilitybasicviews.domain.model

data class Coin(
    val name: String,
    val code: String,
    val description: String,
    var price: Int,
    val imageUrl: String? = null,
    var isFavorite: Boolean = false
)
