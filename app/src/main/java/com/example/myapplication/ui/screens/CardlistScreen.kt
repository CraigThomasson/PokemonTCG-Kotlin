package com.example.myapplication.ui.screens

import MainViewModel
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.BuildConfig
import com.example.myapplication.ErrorScreen
import com.example.myapplication.LoadingScreen
import com.example.myapplication.models.PokemonCard
import com.example.myapplication.models.UiState
import com.example.myapplication.ui.components.CardItem

@Composable
fun CardListScreen(
    setId: String,
    viewModel: MainViewModel,
) {
    val cardUiState by viewModel.cardUiState.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 150.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        when (cardUiState) {
            is UiState.Loading -> {
                item {
                    LoadingScreen()
                }
            }

            is UiState.Success -> {
                val cards = (cardUiState as UiState.Success<List<PokemonCard>>).data
                items(cards) { card ->
                    CardItem(card)
                }
            }

            is UiState.Error -> {
                item {
                    val errorMessage = (cardUiState as UiState.Error).message
                    ErrorScreen(errorMessage)
                }
            }
        }
    }

    val apiKey = BuildConfig.POKEMON_TCG_API_KEY
    LaunchedEffect(setId, apiKey) {
        viewModel.loadCardsBySetId(apiKey, setId)
    }
}