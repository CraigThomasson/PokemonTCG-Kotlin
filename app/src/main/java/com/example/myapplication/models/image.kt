package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val symbolUrl: String,
    val logoUrl: String,
    val smallImageUrl: String,
    val largeImageUrl: String
)