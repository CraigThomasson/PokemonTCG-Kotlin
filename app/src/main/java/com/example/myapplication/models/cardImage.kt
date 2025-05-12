package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class CardImage(
    val small: String? = null,
    val large: String? = null
)