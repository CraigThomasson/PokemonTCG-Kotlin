package com.example.myapplication.models

import kotlinx.serialization.Serializable

@Serializable
data class SetImage(
    val symbol: String,
    val logo: String,
)