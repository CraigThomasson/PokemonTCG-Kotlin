package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class Card(
    val id: String,
    val name: String,
    val imageUrl: String,
    val set: Set
)