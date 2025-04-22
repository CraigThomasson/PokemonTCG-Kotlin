package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class PokemonCard(
    val id: String,
    val name: String,
    val set: Set,
    val cardImage: CardImage,
)