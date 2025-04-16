package com.example.myapplication

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.models.Card
import com.example.myapplication.models.Set

suspend fun fetchPokemonData(apiKey: String): List<Card> {
    val response = RetrofitInstance.api.getPokemonCards(apiKey)
    return response.data
}

suspend fun fetchPokemonSets(apiKey: String): List<Set> {
    val response = RetrofitInstance.api.getPokemonSets(apiKey)
    return response.data
}
