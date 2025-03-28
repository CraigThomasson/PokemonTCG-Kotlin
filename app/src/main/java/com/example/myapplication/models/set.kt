package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class Set(
    val id: String,
    val name: String,
    val series: String,
    val images: String
)