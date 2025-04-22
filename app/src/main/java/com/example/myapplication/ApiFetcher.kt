package com.example.myapplication

import com.example.myapplication.api.RetrofitInstance
import com.example.myapplication.models.PokemonCard
import com.example.myapplication.models.Set

suspend fun fetchPokemonData(apiKey: String): List<PokemonCard> {
    val response = RetrofitInstance.api.getPokemonCards(apiKey)
    return response.data
}

suspend fun fetchPokemonSets(apiKey: String): List<Set> {
    val response = RetrofitInstance.api.getPokemonSets(apiKey)
    return response.data
}

suspend fun fetchCardsBySetId(apiKey: String, setId: String): List<PokemonCard> {
    val query = "set.id:$setId"
    val response = RetrofitInstance.api.getCardsBySetId(apiKey, query)
    return response.data
}