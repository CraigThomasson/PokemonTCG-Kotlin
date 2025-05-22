package com.example.myapplication.ui.screens

import MainViewModel
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.myapplication.ErrorScreen
import com.example.myapplication.LoadingScreen
import com.example.myapplication.SetList
import com.example.myapplication.models.Set
import com.example.myapplication.models.UiState

@Composable
fun SetListScreen(
    viewModel: MainViewModel,
    navController: NavHostController,
) {
    val setUiState by viewModel.setUiState.collectAsState()

    Column(
        modifier =
            Modifier
                .fillMaxSize(),
    ) {
        when (setUiState) {
            is UiState.Loading -> LoadingScreen()
            is UiState.Success -> {
                val sets = (setUiState as UiState.Success<List<Set>>).data
                SetList(sets) { setId ->
                    navController.navigate("cardListScreen/$setId")
                }
            }

            is UiState.Error -> {
                val errorMessage = (setUiState as UiState.Error).message
                ErrorScreen(errorMessage)
            }
        }
    }
}
