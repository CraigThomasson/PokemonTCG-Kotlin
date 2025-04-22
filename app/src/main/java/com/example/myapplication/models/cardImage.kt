package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class CardImage(
    val small: String,
    val large: String,
)